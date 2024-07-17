package com.github.fengye.starring.uranium.api.event;

public abstract class Event {
    private final Class<?> aClass;

    public Event(Class<?> aClass) {
        this.aClass = aClass;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
