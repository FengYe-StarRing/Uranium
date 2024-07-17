package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Numbers;

public class FloatValue extends Numbers<Float> {
    public FloatValue(String name, Float value, Float min, Float max, Float inc) {
        super(name, value, min, max, inc);
    }
}
