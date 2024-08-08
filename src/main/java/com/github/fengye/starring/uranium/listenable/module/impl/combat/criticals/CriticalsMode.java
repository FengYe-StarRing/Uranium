package com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals;

import com.github.fengye.starring.uranium.listenable.module.ModuleInMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.Criticals;
import net.minecraft.network.play.client.C03PacketPlayer;

public class CriticalsMode extends ModuleInMode {
    public void makeCrit() {

    }

    protected void sendCriticalPacket(double yOffset, boolean ground) {
        if(mc.thePlayer == null) {
            return;
        }
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY + yOffset;
        double z = mc.thePlayer.posZ;
        switch ((Criticals.PacketModes)Criticals.critPacketModeValue.get()) {
            case C04:
                sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x,y,z,ground));
                break;
            case C06:
                sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(x,y,z,thePlayer.rotationYaw,thePlayer.rotationPitch,ground));
                break;
        }
    }

    protected void sendCriticalPacket(double yOffset) {
        sendCriticalPacket(yOffset,false);
    }
}
