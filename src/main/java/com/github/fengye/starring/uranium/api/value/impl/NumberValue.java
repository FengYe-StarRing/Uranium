package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Numbers;

public class NumberValue extends Numbers<Number> {
    public NumberValue(String name, Number value, Number min, Number max, Number inc) {
        super(name, value, min, max, inc);
    }
}
