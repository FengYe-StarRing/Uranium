package com.github.fengye.starring.uranium.utils;

import net.minecraft.client.Minecraft;

public abstract class MinecraftInstance {
    public static Minecraft mc;

    public static void init() {
        mc = Minecraft.getMinecraft();
    }
}
