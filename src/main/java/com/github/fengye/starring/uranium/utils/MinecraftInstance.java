package com.github.fengye.starring.uranium.utils;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;

public class MinecraftInstance implements Listenable {
    public static Minecraft mc;
    public static EntityPlayerSP thePlayer;
    public static WorldClient theWorld;

    public static boolean isV189() {
        return getVersion() == ViaMCP.NATIVE_VERSION;
    }

    public static int getVersion() {
        return ViaLoadingBase.getInstance().getTargetVersion().getVersion();
    }

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

    @EventHandle
    private void onEvent(Event event) {
        mc = Minecraft.getMinecraft();
        thePlayer = mc.thePlayer;
        theWorld = mc.theWorld;
    }
}
