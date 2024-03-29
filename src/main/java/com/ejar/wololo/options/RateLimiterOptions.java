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

public class RateLimiterOptions {

    private int capacity;
    private int durationMinutes;

    public RateLimiterOptions() {

        this.capacity = 15;
        this.durationMinutes = 10;

    }

    public int getCapacity() {

        return capacity;

    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;

    }

    public int getDurationMinutes() {

        return durationMinutes;

    }

    public void setDurationMinutes(int durationMinutes) {

        this.durationMinutes = durationMinutes;

    }

    @Override
    public String toString() {

        return "RateLimiterOptions{" +
               "capacity=" + capacity +
               ", durationMinutes=" + durationMinutes + '}';

    }

}
