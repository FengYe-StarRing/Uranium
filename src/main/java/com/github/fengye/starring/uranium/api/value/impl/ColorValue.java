package com.github.fengye.starring.uranium.api.value.impl;

public class ColorValue extends NumberValue {
    public ColorValue(String name, Integer value) {
        super(name, value, 0, 255, 1);
    }

    public int getValue() {
        return get().intValue();
    }
}
