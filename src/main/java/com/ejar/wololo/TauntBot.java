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
import com.ejar.wololo.options.DiscordOptions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.Compression;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class TauntBot {

    private static final Logger logger = LogManager.getLogger();

    private JDA jda;
    private BotOptions options;

    public TauntBot() { }

    /**
     * Resolves the app configuration file. If none is found, it creates
     * the default configuration and writes it out for user convenience.
     *
     * @return
     *      BotOptions
     * @throws Exception
     *      Error in file i/o or configuration
     */
    private BotOptions resolveConfiguration() throws Exception  {

        BotOptions options = null;

        File configurationFile = new File("./config.yml");

        if (!configurationFile.exists()) {

            options = new BotOptions();

            try {

                logger.info("No default configuration file found. Creating basic config here: {}",
                        configurationFile.getAbsolutePath());

                options.writeConfigurationFile(configurationFile);

            } catch (Exception e) {

                logger.error("Failed to write default configuration file to location: {}",
                        configurationFile.getAbsolutePath());

                logger.debug("{}", ExceptionUtils.getStackTrace(e));

                throw new Exception(e.getMessage());

            }

        } else {

            try {

                logger.info("Using configuration file: {}",
                        configurationFile.getAbsolutePath());

                options = new BotOptions().fromConfigurationFile(configurationFile);

            } catch (Exception e) {

                logger.error("Failed to read in existing configuration file: {}",
                        configurationFile.getAbsolutePath());

                logger.debug("{}", ExceptionUtils.getStackTrace(e));

                throw new Exception(e.getMessage());

            }

        }

        return options;

    }

    public void run() throws Exception {

        this.options = this.resolveConfiguration();
        logger.debug("Ingested options: {}", this.options.toString());

        DiscordOptions discordOptions = this.options.getDiscordOptions();

        this.jda = JDABuilder.createDefault(discordOptions.getToken())
                .addEventListeners(new MessageListener(this.options))
                .setCompression(discordOptions.isCompressionEnabled() ? Compression.ZLIB : Compression.NONE)
                .setAutoReconnect(discordOptions.isAutoReconnect())
                .build();

        this.jda.awaitReady();

    }

}
