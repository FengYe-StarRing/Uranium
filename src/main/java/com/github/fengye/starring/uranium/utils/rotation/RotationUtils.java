package com.github.fengye.starring.uranium.utils.rotation;

import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class RotationUtils extends MinecraftInstance {
    public static Rotation getRotationEntity(Entity vec) {
        double y = vec.posY - thePlayer.posY + thePlayer.getEyeHeight();
        double x = vec.posX - thePlayer.posX;
        double z = vec.posZ - thePlayer.posZ;
        double dff = Math.sqrt(x * x + z * z);
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(y, dff)));
        return new Rotation(new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)});
    }
}
