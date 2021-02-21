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

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class MessageLexer implements Runnable {

    private final Logger logger = LogManager.getLogger();

    private MessageReceivedEvent event;
    private TauntsDatabase tauntsDatabase;

    public MessageLexer(MessageReceivedEvent event) {

        this.event = event;
        this.tauntsDatabase = TauntsDatabase.getInstance();

    }

    @Override
    public void run() {

        if (this.event == null) {

            return;

        }

        String messageInput = this.event.getMessage().getContentRaw();
        logger.debug("Parsing Message: {}", messageInput);

        String[] embeddedIntegers = messageInput.split("[^0-9]+");
        logger.debug("Found integers (len={}): {}", embeddedIntegers.length,
                ArrayUtils.toString(embeddedIntegers));

        if (embeddedIntegers.length == 0) {

            return;

        }

        String messageOutput = messageInput;

        for (int i = 0; i < embeddedIntegers.length; i++) {

            String s = embeddedIntegers[i];

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


            messageOutput = messageOutput.replaceFirst(embeddedIntegers[i], replacement);

        }

        event.getGuild().getTextChannelById(event.getMessage().getTextChannel().getId())
                .sendMessage(messageOutput).reference(event.getMessage()).queue();

    }

}
