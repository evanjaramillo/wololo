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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.util.Arrays;

public class BotOptions {

    private ObjectMapper YML_OBJECT_MAPPER;

    private ThreadPoolOptions threadPoolOptions;
    private DiscordOptions discordOptions;
    private String[] listeningMessageChannels;
    private String operatingMode;

    public BotOptions() {

        this.init();

    }

    private void init() {

        this.threadPoolOptions = new ThreadPoolOptions();
        this.discordOptions = new DiscordOptions();
        this.operatingMode = OperatingMode.REPLY.getMode();
        this.listeningMessageChannels = new String[0];

        YAMLFactory yf = new YAMLFactory();

        // use platform dependent line endings for Notepad's sake
        yf.configure(YAMLGenerator.Feature.USE_PLATFORM_LINE_BREAKS, true);

        this.YML_OBJECT_MAPPER = new ObjectMapper(yf);

    }

    public BotOptions fromConfigurationFile(File f) throws Exception {

        return YML_OBJECT_MAPPER.readValue(f, BotOptions.class);

    }

    public void writeConfigurationFile(File f) throws Exception {

        YML_OBJECT_MAPPER.writeValue(f, this);

    }

    public ThreadPoolOptions getThreadPoolOptions() {

        return threadPoolOptions;

    }

    public void setThreadPoolOptions(ThreadPoolOptions threadPoolOptions) {

        this.threadPoolOptions = threadPoolOptions;

    }

    public DiscordOptions getDiscordOptions() {

        return discordOptions;

    }

    public void setDiscordOptions(DiscordOptions discordOptions) {

        this.discordOptions = discordOptions;

    }

    public String[] getListeningMessageChannels() {

        return listeningMessageChannels;

    }

    public void setListeningMessageChannels(String[] listeningMessageChannels) {

        this.listeningMessageChannels = listeningMessageChannels;

    }

    public String getOperatingMode() {

        return operatingMode;

    }

    public void setOperatingMode(String operatingMode) {

        this.operatingMode = operatingMode;

    }

    @Override
    public String toString() {

        return "BotOptions{" +
                "threadPoolOptions=" + threadPoolOptions +
                ", discordOptions=" + discordOptions +
                ", listeningMessageChannels=" + Arrays.toString(listeningMessageChannels) +
                ", operatingMode='" + operatingMode + '\'' +
                '}';

    }

}
