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
