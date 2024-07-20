package com.github.fengye.starring.uranium.api.value;

public abstract class Value<V> {
    private String name;
    private V value;
    private boolean display;

    public Value(String name,V value) {
        this.name = name;
        this.value = value;
        display = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V get() {
        return value;
    }

    public void set(V value) {
        this.value = value;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public abstract void setAuto(String value); // 自动适配
}
