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

package com.ejar.wololo.options;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OperatingMode {

    /*
     * This mode will simply reply to the user who triggers the bot.
     */
    REPLY("REPLY"),

    /*
     * This mode will remove the message that triggers the bot and the
     * bot will add a message with the taunts in place.
     */
    REPLACE("REPLACE"),

    /*
     * * CURRENTLY UNSUPPORTED *
     * This mode is for the potential 'message editing' feature that the discord
     * developers may or may not add. Its goal is to have the bot edit the original
     * message and add the taunts.
     */
    INLINE("INLINE");

    private static final Logger logger = LogManager.getLogger();

    private final String mode;

    private OperatingMode(String mode) {

        this.mode = mode;

    }

    public String getMode() {

        return mode;

    }

    public static OperatingMode deduceOperatingMode(String mode) {

        String formattedMode = mode.replaceAll(" ", "")
                .toUpperCase();

        OperatingMode operatingMode = null;

        switch (formattedMode) {

            case "REPLY": {
                operatingMode = OperatingMode.REPLY;
                break;
            }

            case "REPLACE": {
                operatingMode = OperatingMode.REPLACE;
                break;
            }

            case "INLINE": {
                operatingMode = OperatingMode.INLINE;
                break;
            }

            default: {
                logger.warn("No candidate for mode '{}' defaulting to 'REPLY'", mode);
                operatingMode = OperatingMode.REPLY;
            }

        }

        return operatingMode;

    }

}
