package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Value;

public class OptionValue extends Value<Boolean> {
    public OptionValue(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public void setAuto(String value) {
        set(Boolean.valueOf(value));
    }
}
