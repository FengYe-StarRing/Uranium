package com.github.fengye.starring.uranium.utils.rotation;

import net.minecraft.entity.player.EntityPlayer;

public class Rotation {
    private float yaw,pitch;

    public Rotation(float yaw,float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Rotation(float[] rots) {
        yaw = rots[0];
        pitch = rots[1];
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

    public void toPlayer(EntityPlayer player) {
        player.rotationYaw = yaw;
        player.rotationPitch = pitch;
    }
}
