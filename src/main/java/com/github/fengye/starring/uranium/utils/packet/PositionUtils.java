package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.utils.rotation.Rotation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class PositionUtils extends PacketUtils implements Listenable {
    private static double x,y,z;
    private static float yaw,pitch;
    private static boolean moving,rotating,onGround;

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
    private static void setPosition(double x,double y,double z,float yaw,float pitch,boolean moving,boolean rotating,boolean onGround) {
        PositionUtils.x = x;
        PositionUtils.y = y;
        PositionUtils.z = z;
        PositionUtils.yaw = yaw;
        PositionUtils.pitch = pitch;
        PositionUtils.moving = moving;
        PositionUtils.rotating = rotating;
        PositionUtils.onGround = onGround;
    }

    // C04
    private static void setPosition(double x,double y,double z,boolean onGround) {
        setPosition(x,y,z,yaw,pitch,true,rotating,onGround);
    }

    // C05
    private static void setPosition(float yaw,float pitch,boolean onGround) {
        setPosition(x,y,z,yaw,pitch,moving,true,onGround);
    }

    // C06
    private static void setPosition(double x,double y,double z,float yaw,float pitch,boolean onGround) {
        setPosition(x,y,z,yaw,pitch,true,true,onGround);
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static double getZ() {
        return z;
    }

    public static boolean isMoving() {
        return moving;
    }

    public static boolean isRotating() {
        return rotating;
    }

    public static boolean isOnGround() {
        return onGround;
    }

    public static float getYaw() {
        return yaw;
    }

    public static float getPitch() {
        return pitch;
    }

    public static Rotation getRotation() {
        return new Rotation(yaw,pitch);
    }
}
