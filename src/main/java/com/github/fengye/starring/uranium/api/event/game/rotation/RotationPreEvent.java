package com.github.fengye.starring.uranium.api.event.game.rotation;

import com.github.fengye.starring.uranium.api.event.EventState;

public class RotationPreEvent extends RotationEvent {
    public RotationPreEvent(float yaw,float pitch) {
        super(EventState.Pre,yaw,pitch);
    }
}
