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
        return String.valueOf(get());
    }

    public void set(String mode) {
        for (Enum<?> anEnum : MODES) {
            if(anEnum.name().equals(mode)) {
                set(anEnum);
            }
        }
    }

    public void switchMode(boolean left) {
        int index = 0;
        for (Enum<?> mode : MODES) {
            if(getAsString().equals(mode.name())) {
                break;
            }
            index++;
        }
        index += left ? -1 : 1;
        if(index == -1) {
            index = MODES.length - 1;
        }
        if(index == MODES.length) {
            index = 0;
        }
        set(MODES[index]);
    }

    @Override
    public void setAuto(String value) {
        set(value);
    }
}
