package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Numbers;

public class ColorValue extends Numbers<Integer> {
    public ColorValue(String name, Integer value) {
        super(name, value, 0, 255, 1);
    }
}
