package com.github.fengye.starring.uranium.api.value;

public abstract class Numbers<T extends Number> extends Value<T>{
    private final T min;
    private final T max;
    private final T inc;

    public Numbers(String name, T value, T min, T max, T inc) {
        super(name, value);
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getInc() {
        return inc;
    }
}
