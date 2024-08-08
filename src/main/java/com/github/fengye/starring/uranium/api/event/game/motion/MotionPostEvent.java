package com.github.fengye.starring.uranium.api.event.game.motion;

import com.github.fengye.starring.uranium.api.event.EventState;

public class MotionPostEvent extends MotionEvent{
    public MotionPostEvent() {
        super(EventState.Post);
    }
}
