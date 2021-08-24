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
import com.ejar.wololo.options.ThreadPoolOptions;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MessageListener extends ListenerAdapter {

    private final Logger logger = LogManager.getLogger();

    private final BotOptions botOptions;
    private final ExecutorService executorService;
    private final RateLimiterDatabase rateLimiterDatabase;

    public MessageListener(BotOptions options) {

        this.botOptions = options;

        ThreadPoolOptions threadingOptions = this.botOptions.getThreadPoolOptions();

        ThreadFactory tf = new BasicThreadFactory.Builder()
                .namingPattern(threadingOptions.getThreadNomenclature())
                .daemon(threadingOptions.isDaemon())
                .build();

        this.executorService = Executors.newCachedThreadPool(tf);
        this.rateLimiterDatabase = RateLimiterDatabase.getInstance();

    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        User author = event.getAuthor();

        if (author.isBot()) {

            return;

        }

        this.rateLimiterDatabase.registerRateLimit(author);

        if (!this.rateLimiterDatabase.hasAvailableApiTokens(author)) {

            return;

        }

        String channel = event.getChannel().getName();

        if (ArrayUtils.contains(this.botOptions.getListeningMessageChannels(),
                channel)) {

            this.executorService.submit(new MessageProcessor(event, this.botOptions));

        }

    }

}
