package com.github.fengye.starring.uranium.api.event.game.rotation;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventState;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.packet.PositionUtils;
import com.github.fengye.starring.uranium.utils.rotation.Rotation;
import net.minecraft.client.entity.EntityPlayerSP;

public class RotationEvent extends Event {
    private final EventState state;
    private float yaw,pitch;

    public RotationEvent(EventState state,float yaw,float pitch) {
        super(RotationEvent.class);
        this.state = state;
        this.yaw = yaw;
        this.pitch = pitch;

        EntityPlayerSP thePlayer = MinecraftInstance.thePlayer;
        thePlayer.renderYawOffset = PositionUtils.getYaw();
        thePlayer.rotationYawHead = PositionUtils.getYaw();
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

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setRotation(Rotation rotation) {
        if(rotation == null) {
            return;
        }
        yaw = rotation.getYaw();
        pitch = rotation.getPitch();
    }
}
