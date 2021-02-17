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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class App {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        logger.info("Wololo!");

        try {

            BotOptions botOptions = null;

            File configurationFile = new File("./config.yml");

            if (!configurationFile.exists()) {

                botOptions = new BotOptions();
                botOptions.writeConfigurationFile(configurationFile);

            }

            TauntBot bot = new TauntBot(botOptions);

        } catch (Throwable e) {

            logger.error("Unhandled exception: {}", e.getMessage());
            logger.trace("{}", ExceptionUtils.getStackTrace(e));

        }



    }

}
