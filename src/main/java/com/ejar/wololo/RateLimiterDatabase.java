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

import com.ejar.wololo.options.RateLimiterOptions;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import net.dv8tion.jda.api.entities.User;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimiterDatabase {

    private static RateLimiterDatabase instance = null;

    private final Map<String, Bucket> rateLimitEntries;
    private final ReentrantLock queryLock;

    private RateLimiterOptions options;

    private RateLimiterDatabase() {

        this.queryLock = new ReentrantLock();
        this.rateLimitEntries = new ConcurrentHashMap<>();

    }

    public static RateLimiterDatabase getInstance() {

        if (instance == null) {

            instance = new RateLimiterDatabase();

        }

        return instance;

    }

    public void setOptions(RateLimiterOptions options) {

        if (this.options != null) {

            // don't reset the options
            return;

        }

        this.options = options;

    }

    public void registerRateLimit(User user) {

        if (user == null) {

            return;

        }

        String id = user.getId();

        if (!this.rateLimitEntries.containsKey(id)) {

            Refill refill = Refill.intervally(this.options.getCapacity(),
                    Duration.ofMinutes(this.options.getDurationMinutes()));

            Bandwidth limiter = Bandwidth.classic(this.options.getCapacity(), refill);

            Bucket bucket = Bucket4j.builder().addLimit(limiter).build();

            this.rateLimitEntries.put(id, bucket);

        }

    }

    public boolean hasAvailableApiTokens(User user) {

        Bucket b = this.rateLimitEntries.get(user.getId());

        return b.getAvailableTokens() > 0;

    }

    boolean tryApiConsume(User user) {

        return this.rateLimitEntries.get(user.getId()).tryConsume(1);

    }

}
