package com.github.fengye.starring.uranium.api.event.game.motion;

import com.github.fengye.starring.uranium.api.event.EventState;

public class MotionPreEvent extends MotionEvent{
    public MotionPreEvent() {
        super(EventState.Pre);
    }
}
