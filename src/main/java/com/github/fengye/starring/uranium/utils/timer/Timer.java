package com.github.fengye.starring.uranium.utils.timer;

public class Timer {
    private long time = -1L; // ms
    private long tick = 0; // tick
    private long count = 0; // frequency

    public void reset() {
        time = getCurrentTimeMillis();
        tick = 0;
        count = 0;
    }

    private long getCurrentTimeMillis() {
        count++;
        return System.currentTimeMillis();
    }

    public boolean hasTimePassed(long MS) {
        count++;
        return getCurrentTimeMillis() >= time + MS;
    }

    public boolean hasTickPassed(long tick) {
        return tick >= this.tick;
    }

    public long getFrequency() {
        return count;
    }

    public void addTick(long ticks) {
        tick += ticks;
    }

    public void addTick() {
        addTick(1);
    }
}
