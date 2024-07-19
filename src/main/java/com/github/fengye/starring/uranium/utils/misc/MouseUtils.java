package com.github.fengye.starring.uranium.utils.misc;

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
}
