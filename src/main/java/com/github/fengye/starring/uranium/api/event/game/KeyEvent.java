package com.github.fengye.starring.uranium.api.event.game;

import com.github.fengye.starring.uranium.api.event.Event;

public class KeyEvent extends Event {
    private int key;

    public KeyEvent(int key) {
        super(KeyEvent.class);
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
