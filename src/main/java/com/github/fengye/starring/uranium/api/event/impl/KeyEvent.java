package com.github.fengye.starring.uranium.api.event.impl;

import com.github.fengye.starring.uranium.api.event.Event;

public class KeyEvent extends Event {
    private int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
