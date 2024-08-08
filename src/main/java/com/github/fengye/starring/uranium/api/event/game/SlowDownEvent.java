package com.github.fengye.starring.uranium.api.event.game;

import com.github.fengye.starring.uranium.api.event.Event;

public class SlowDownEvent extends Event {
    private float strafe;
    private float forward;

    public SlowDownEvent(float strafe,float forward) {
        super(SlowDownEvent.class);
        this.strafe = strafe;
        this.forward = forward;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getStrafe() {
        return strafe;
    }

    public float getForward() {
        return forward;
    }
}
