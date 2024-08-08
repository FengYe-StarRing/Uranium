package com.github.fengye.starring.uranium.utils.entity;

import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import net.minecraft.entity.Entity;

public class MovementUtils extends MinecraftInstance {
    public static boolean isMoving() {
        return movementInput != null && (movementInput.moveStrafe != 0 || movementInput.moveForward != 0);
    }

    public static double getMoveSpeed(Entity entity) {
        double xDist = entity.posX - entity.lastTickPosX;
        double zDist = entity.posZ - entity.lastTickPosZ;
        return (Math.sqrt(xDist * xDist + zDist * zDist) * 2 * ((entity.getEntityId() == thePlayer.getEntityId()) ? mc.timer.timerSpeed : 1)) * 10;
    }

    public static double getMoveSpeed() {
        return getMoveSpeed(thePlayer);
    }
}
