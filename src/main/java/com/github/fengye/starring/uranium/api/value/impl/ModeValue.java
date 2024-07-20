package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.ArrayValue;

import java.util.ArrayList;
import java.util.List;

public class ModeValue extends ArrayValue<Enum<?>> {
    public ModeValue(String name, Enum<?>[] modes,Enum<?> value) {
        super(name,modes,value);
    }

    @Override
    public String getAsString() {
        return String.valueOf(get());
    }

    @Override
    public void setAuto(String value) {
        set(value);
    }

    @Override
    public void set(String value) {
        for (Enum<?> mode : getModes()) {
            if(mode.name().equals(value)) {
                set(mode);
            }
        }
    }

    @Override
    public String[] getModesAsStr() {
        List<String> strings = new ArrayList<>();
        for (Enum<?> mode : getModes()) {
            strings.add(String.valueOf(mode));
        }
        return strings.toArray(new String[0]);
    }
}
