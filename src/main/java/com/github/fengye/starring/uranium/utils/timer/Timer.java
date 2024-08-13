package com.github.fengye.starring.uranium.utils.timer;

public class Timer {
    private long time = -1L; // ms
    private long tick = 0; // tick
    private long count = 0; // frequency

    public Timer() {
        reset();
    }

    public void reset() {
        time = getCurrentTimeMillis();
        tick = 0;
        count = 0;
    }

    private long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public boolean hasTimePassed(long ms) {
        count++;
        return getCurrentTimeMillis() >= time + ms;
    }

    public boolean hasTickPassed(long tick) {
        count++;
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
