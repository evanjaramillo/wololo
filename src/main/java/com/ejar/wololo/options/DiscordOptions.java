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

public class DiscordOptions {

    private String token;
    private boolean compressionEnabled;
    private boolean autoReconnect;

    public DiscordOptions() {

        this.init();

    }

    private void init() {

        this.token = "NO TOKEN!";
        this.compressionEnabled = true;

    }

    public String getToken() {

        return token;

    }

    public void setToken(String token) {

        this.token = token;

    }

    public boolean isCompressionEnabled() {

        return compressionEnabled;

    }

    public void setCompressionEnabled(boolean compressionEnabled) {

        this.compressionEnabled = compressionEnabled;

    }

    public boolean isAutoReconnect() {

        return autoReconnect;

    }

    public void setAutoReconnect(boolean autoReconnect) {

        this.autoReconnect = autoReconnect;

    }

    @Override
    public String toString() {

        return "DiscordOptions{" +
                "token='" + token + '\'' +
                ", compressionEnabled=" + compressionEnabled +
                ", autoReconnect=" + autoReconnect +
                '}';

    }

}
