package com.github.fengye.starring.uranium.api.event.impl;

import com.github.fengye.starring.uranium.api.event.CancellableEvent;

public class ChatEvent extends CancellableEvent {
    private String message;
    private final ChatMode MODE;

    public ChatEvent(String message,ChatMode mode) {
        this.message = message;
        MODE = mode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelf() {
        return MODE.equals(ChatMode.Self);
    }

    public enum ChatMode {
        Self
    }
}
