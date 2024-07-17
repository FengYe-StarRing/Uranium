package com.github.fengye.starring.uranium.api.value;

public abstract class Value<V> {
    public final String NAME;
    private String name;
    private V value;
    private boolean display;

    public Value(String name,V value) {
        NAME = name;
        this.name = NAME;
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
}
