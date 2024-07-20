package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.api.value.Value;

public class TextValue extends Value<String> {
    public TextValue(String name, String value) {
        super(name, value);
    }

    @Override
    public void setAuto(String value) {
        set(value);
    }

    @Override
    public String getAsString() {
        return get();
    }
}
