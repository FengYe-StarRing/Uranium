package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Numbers;

public class IntValue extends Numbers<Integer> {
    public IntValue(String name, Integer value, Integer min, Integer max, Integer inc) {
        super(name, value, min, max, inc);
    }
}
