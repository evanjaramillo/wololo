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

public class ThreadPoolOptions {

    private String threadNomenclature;
    private boolean daemon;

    public ThreadPoolOptions() {

        this.init();

    }

    private void init() {

        this.threadNomenclature = "wololo-bot-%d";
        this.daemon = true;

    }

    public String getThreadNomenclature() {

        return threadNomenclature;

    }

    public void setThreadNomenclature(String threadNomenclature) {

        this.threadNomenclature = threadNomenclature;

    }

    public boolean isDaemon() {

        return daemon;

    }

    public void setDaemon(boolean daemon) {

        this.daemon = daemon;

    }

    @Override
    public String toString() {
        return "ThreadPoolOptions{" +
                ", threadNomenclature='" + threadNomenclature + '\'' +
                ", daemon=" + daemon +
                '}';
    }

}
