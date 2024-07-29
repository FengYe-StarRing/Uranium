package com.github.fengye.starring.uranium.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenUtils {
    public static int getWidth() {
        return getScaledResolution().getScaledWidth();
    }

    public static int getHeight() {
        return getScaledResolution().getScaledHeight();
    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }
}
