package com.github.fengye.starring.uranium.utils.entity;

import com.github.fengye.starring.uranium.utils.MinecraftInstance;

public class MovementUtils extends MinecraftInstance {
    public static boolean isMoving() {
        return movementInput != null && (movementInput.moveForward != 0 || movementInput.moveStrafe != 0);
    }
}
