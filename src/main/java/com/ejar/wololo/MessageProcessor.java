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

import com.ejar.wololo.options.BotOptions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class MessageProcessor implements Runnable {

    private final Logger logger = LogManager.getLogger();

    private MessageReceivedEvent messageReceivedEvent;
    private BotOptions options;
    private TauntsDatabase tauntsDatabase;

    public MessageProcessor(MessageReceivedEvent event, BotOptions options) {

        this.init();

        this.messageReceivedEvent = event;
        this.options = options;

    }

    private void init() {

        this.tauntsDatabase = TauntsDatabase.getInstance();

    }

    @Override
    public void run() {

        try {

            if (this.messageReceivedEvent == null) {

                return;

            }

            String messageInput = this.messageReceivedEvent.getMessage().getContentRaw();
            logger.debug("Parsing Message: {}", messageInput);

            String[] embeddedIntegers = messageInput.split("[^0-9]+");
            logger.debug("Found integers (len={}): {}", embeddedIntegers.length,
                    ArrayUtils.toString(embeddedIntegers));

            if (embeddedIntegers.length == 0) {

                return;

            }

            if ( embeddedIntegers.length > this.options.getMaxMessageTauntCount()) {

                AbuseResponder responder = new AbuseResponder(messageReceivedEvent.getAuthor());
                responder.respond();

                return;

            }

            String messageOutput = messageInput;

            boolean outputChanged = false;

            for (String s : embeddedIntegers) {

                if (s.isEmpty()) {

                    continue;

                }

                int key = Integer.parseInt(s);

                String replacement = null;

                try {

                    replacement = this.tauntsDatabase.getTauntForInteger(key);

                } catch (SQLException e) {

                    logger.warn("Unable to get taunt for key: {}", key);
                    logger.debug("{}", ExceptionUtils.getStackTrace(e));

                    continue;

                }

                if (replacement == null) {

                    continue;

                }

                outputChanged = true;
                messageOutput = messageOutput.replaceFirst(s, replacement);

            }

            if (!outputChanged) {

                return;

            }

            messageReceivedEvent.getGuild().getTextChannelById(messageReceivedEvent.getMessage().getTextChannel().getId())
                    .sendMessage(messageOutput).reference(messageReceivedEvent.getMessage()).queue();

        } catch (Exception e) {

            logger.error("Error in message processor: {}", e.getMessage());
            logger.debug("{}", ExceptionUtils.getStackTrace(e));

        }

    }

}
