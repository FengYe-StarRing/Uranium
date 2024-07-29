package com.github.fengye.starring.uranium.utils.misc;

import com.github.fengye.starring.uranium.utils.render.ScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class MouseUtils {
    public static boolean isLeftClickDown() {
        return isButtonDown(0);
    }

    public static boolean isButtonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    public static boolean isHovered(float x, float y, float x2, float y2) {
        return isHovered(x,y,x2,y2,getMouseX(),getMouseY());
    }

    public static int getMouseX() {
        return Mouse.getX() * ScreenUtils.getScaledResolution().getScaledWidth() / Minecraft.getMinecraft().displayWidth;
    }

    public static int getMouseY() {
        int height = ScreenUtils.getScaledResolution().getScaledHeight();
        return height - Mouse.getY() * height / Minecraft.getMinecraft().displayHeight - 1;
    }
}
