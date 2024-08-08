package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class PositionUtils implements Listenable {
    private double x,y,z;
    private float yaw,pitch;
    private boolean moving,rotating,onGround;

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle(priority = Priority.MINIMUM)
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(event.isCancelled()) {
            return;
        }
        if(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) {
            C03PacketPlayer.C04PacketPlayerPosition c04 = (C03PacketPlayer.C04PacketPlayerPosition) packet;
            setPosition(c04.getPositionX(),c04.getPositionY(),c04.getPositionZ(),c04.isOnGround());
        }
        if(packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
            C03PacketPlayer.C05PacketPlayerLook c05 = (C03PacketPlayer.C05PacketPlayerLook) packet;
            setPosition(c05.getYaw(),c05.getPitch(),c05.isOnGround());
        }
        if(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            C03PacketPlayer.C06PacketPlayerPosLook c06 = (C03PacketPlayer.C06PacketPlayerPosLook) packet;
            setPosition(c06.getPositionX(),c06.getPositionY(),c06.getPositionZ(),c06.getYaw(),c06.getPitch(),c06.isOnGround());
        }
    }

    @EventHandle
    private void onPacketRecieve(PacketRecieveEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook) packet;
            setPosition(s08.getX(),s08.getY(),s08.getZ(),s08.getYaw(),s08.getPitch(),onGround);
        }
    }

    // C03
    private void setPosition(double x,double y,double z,float yaw,float pitch,boolean moving,boolean rotating,boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.moving = moving;
        this.rotating = rotating;
        this.onGround = onGround;
    }

    // C04
    private void setPosition(double x,double y,double z,boolean onGround) {
        setPosition(x,y,z,yaw,pitch,true,rotating,onGround);
    }

    // C05
    private void setPosition(float yaw,float pitch,boolean onGround) {
        setPosition(x,y,z,yaw,pitch,moving,true,onGround);
    }

    // C06
    private void setPosition(double x,double y,double z,float yaw,float pitch,boolean onGround) {
        setPosition(x,y,z,yaw,pitch,true,true,onGround);
    }
}
