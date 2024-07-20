package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Numbers;

public class NumberValue extends Numbers<Double> {
    public NumberValue(String name, Double value, Double min, Double max, Double inc) {
        super(name, value, min, max, inc);
    }

    public NumberValue(String name, Number value, Number min, Number max, Number inc) {
        super(name, value.doubleValue(), min.doubleValue(), max.doubleValue(), inc.doubleValue());
    }

    @Override
    public String getAsString() {
        return String.valueOf(get());
    }
}
