package com.github.fengye.starring.uranium.api.event.impl.motion;

import com.github.fengye.starring.uranium.api.event.Event;

public class MotionEvent extends Event {
    private final EventState state;
    public static EventState eventState;

    public MotionEvent(EventState state) {
        super(MotionEvent.class);
        this.state = state;
        eventState = state;
    }

    public EventState getState() {
        return state;
    }

    public boolean isPre() {
        return state.equals(EventState.Pre);
    }

    public boolean isPost() {
        return state.equals(EventState.Post);
    }
}
