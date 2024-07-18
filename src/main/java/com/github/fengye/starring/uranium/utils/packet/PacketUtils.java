package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import net.minecraft.network.Packet;

public class PacketUtils extends MinecraftInstance {
    private static Packet<?> intervalPacket = null;

    public static void sendInterval() {
        if(intervalPacket == null) {
            return;
        }
        sendPacket(intervalPacket);
    }
}
