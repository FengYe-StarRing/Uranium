package com.github.fengye.starring.uranium.api.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Event {
    private final List<Class<?>> classList = new ArrayList<>();

    public Event(Class<?> aClass) {
        classList.add(aClass);
    }

    public Event(Class<?>[] aClasses) {
        classList.addAll(Arrays.asList(aClasses));
    }

    public List<Class<?>> getClasses() {
        return classList;
    }
}
