package com.github.fengye.starring.uranium.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenUtils {
    public static int getWidth() {
        return (new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth();
    }

    public static int getHeight() {
        return (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight();
    }
}
