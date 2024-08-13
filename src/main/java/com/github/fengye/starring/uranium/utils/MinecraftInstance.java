package com.github.fengye.starring.uranium.utils;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.WorldServer;

import java.io.File;

public class MinecraftInstance implements Listenable {
    public static Minecraft mc;
    public static EntityPlayerSP thePlayer;
    public static WorldClient theWorld;
    public static File mcDataDir;
    public static Timer timer;
    public static GameSettings gameSettings;
    public static NetHandlerPlayClient netHandler;
    public static MovementInput movementInput;
    public static MovingObjectPosition objectMouseOver;
    public static GuiScreen currentScreen;

    public static MinecraftServer serverMc;
    public static WorldServer serverWorld;
    public static WorldServer[] worldServers;

    public static void sendPacketNoEvent(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void sendPacket(Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(Client.RESOURCES + path);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle(priority = Priority.HIGHEST)
    public void init(Event event) {
        mc = Minecraft.getMinecraft();
        if(mc != null) {
            thePlayer = mc.thePlayer;
            if(thePlayer != null) {
                movementInput = thePlayer.movementInput;
            }
            theWorld = mc.theWorld;
            mcDataDir = mc.mcDataDir;
            timer = mc.timer;
            gameSettings = mc.gameSettings;
            netHandler = mc.getNetHandler();
            objectMouseOver = mc.objectMouseOver;
            currentScreen = mc.currentScreen;
        }
        serverMc = MinecraftServer.getServer();
        if(serverMc != null) {
            worldServers = serverMc.worldServers;
            if(worldServers != null) {
                serverWorld = worldServers[0];
            }
        }
    }

    public static void sendMessage(String message) {
        if(thePlayer == null) {
            return;
        }
        thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static int getFPS() {
        return Minecraft.getDebugFPS();
    }
}
