package com.github.fengye.starring.uranium.api.event.game;

import com.github.fengye.starring.uranium.api.event.Event;

public class Render3DEvent extends Event {
    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        super(Render3DEvent.class);
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
