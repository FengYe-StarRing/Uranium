package com.github.fengye.starring.uranium.utils;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Timer;

import java.io.File;

public class MinecraftInstance implements Listenable {
    public static Minecraft mc;
    public static EntityPlayerSP thePlayer;
    public static WorldClient theWorld;
    public static File mcDataDir;
    public static Timer timer;

    public static void sendPacket(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void sendPacketNoEvent(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle(priority = Priority.HIGHEST)
    private void onEvent(Event event) {
        mc = Minecraft.getMinecraft();
        thePlayer = mc.thePlayer;
        theWorld = mc.theWorld;
        mcDataDir = mc.mcDataDir;
        timer = mc.timer;
    }

    public static void sendMessage(String message) {
        if(thePlayer == null) {
            return;
        }
        thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
