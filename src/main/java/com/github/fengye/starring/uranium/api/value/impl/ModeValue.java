package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Value;

public class ModeValue extends Value<Enum<?>> {
    private final Enum<?>[] MODES;

    public ModeValue(String name, Enum<?>[] modes,Enum<?> value) {
        super(name, value);
        MODES = modes;
    }

    public Enum<?>[] getMODES() {
        return MODES;
    }

    public String getAsString() {
        return get().name();
    }
}
