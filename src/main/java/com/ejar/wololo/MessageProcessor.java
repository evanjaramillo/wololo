////////////////////////////////////////////////////////////////////////////////
//    Copyright 2021 Evan R. Jaramillo
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
////////////////////////////////////////////////////////////////////////////////

package com.ejar.wololo;

import com.ejar.wololo.interfaces.ITauntProcessor;
import com.ejar.wololo.options.BotOptions;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class MessageProcessor implements Runnable {

    private final Logger logger = LogManager.getLogger();

    private final MessageReceivedEvent messageReceivedEvent;
    private final BotOptions options;
    private final RateLimiterDatabase rateLimiterDatabase;

    public MessageProcessor(MessageReceivedEvent event, BotOptions options) {

        this.messageReceivedEvent = event;
        this.options = options;
        this.rateLimiterDatabase = RateLimiterDatabase.getInstance();

    }

    @Override
    public void run() {

        try {

            if (this.messageReceivedEvent == null) {

                return;

            }

            String messageInput = this.messageReceivedEvent.getMessage().getContentRaw();

            char activationChar = this.options.getActivationCharacter();

            if (!messageInput.contains("" + activationChar)) {

                // no commands present
                return;

            }


            ITauntProcessor processor = new DefaultTauntProcessor();

            Map<String, String> taunts = processor.processTaunts(messageInput, activationChar);

            if (taunts == null || taunts.isEmpty()) {

                // nothing to do.
                return;

            }

            String reply = messageInput;

            User author = this.messageReceivedEvent.getAuthor();

            for (Map.Entry<String, String> entry : taunts.entrySet()) {

                while (reply.contains(entry.getKey())) {

                    if (!this.rateLimiterDatabase.tryApiConsume(author) ||
                        !this.rateLimiterDatabase.hasAvailableApiTokens(author)) {

                        AbuseResponder abuse = new AbuseResponder(author);
                        abuse.respond();

                        return;

                    }

                    reply = reply.replaceFirst(entry.getKey(), entry.getValue());

                }

            }

            messageReceivedEvent.getGuild().getTextChannelById(messageReceivedEvent.getMessage().getTextChannel().getId())
                    .sendMessage(reply).reference(messageReceivedEvent.getMessage()).queue();

        } catch (Exception e) {

            logger.error("Error in message processor: {}", e.getMessage());
            logger.debug("{}", ExceptionUtils.getStackTrace(e));

        }

    }

}
