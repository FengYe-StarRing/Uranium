package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Numbers;

public class DoubleValue extends Numbers<Double> {
    public DoubleValue(String name, Double value, Double min, Double max, Double inc) {
        super(name, value, min, max, inc);
    }
}
