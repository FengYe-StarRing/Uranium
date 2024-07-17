package com.github.fengye.starring.uranium.utils;

import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;

public abstract class MinecraftInstance {
    public static Minecraft mc;

    public static void init() {
        mc = Minecraft.getMinecraft();
    }

    public static boolean isV189() {
        return getVersion() == ViaMCP.NATIVE_VERSION;
    }

    public static int getVersion() {
        return ViaLoadingBase.getInstance().getTargetVersion().getVersion();
    }
}
