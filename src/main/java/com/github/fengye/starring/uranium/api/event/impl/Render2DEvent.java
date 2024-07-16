package com.github.fengye.starring.uranium.api.event.impl;

import com.github.fengye.starring.uranium.api.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {
    private final ScaledResolution sr;
    private final float partialTicks;

    public Render2DEvent(ScaledResolution sr,float partialTicks) {
        this.sr = sr;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getSr() {
        return sr;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
