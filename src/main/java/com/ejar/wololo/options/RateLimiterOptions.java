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
