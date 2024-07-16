package com.github.fengye.starring.uranium.api.event.impl;

import com.github.fengye.starring.uranium.api.event.Event;

public class Render3DEvent extends Event {
    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
