package com.github.fengye.starring.uranium.listenable.module.impl.move.speed.impl;

import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.impl.move.speed.SpeedMode;
import com.github.fengye.starring.uranium.utils.entity.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

public class EntityMode extends SpeedMode {
    private final NumberValue boostValue = new NumberValue("Boost",8,1,50,1);

    @Override
    public void onUpdate(UpdateEvent event) {
        double speed = boostValue.get() * collisionEntity();
        double boost = 1 + speed * 0.01;
        thePlayer.motionX *= boost;
        thePlayer.motionZ *= boost;
    }

    private int collisionEntity() {
        int number = 0;
        for (EntityLivingBase entity : EntityUtils.getLivingBaseEntities()) {
            if(entity.getEntityId() != thePlayer.getEntityId()) {
                AxisAlignedBB playerBoundingBox = thePlayer.getEntityBoundingBox();
                AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox();
                if(playerBoundingBox.intersectsWith(entityBoundingBox)) {
                    number++;
                }
            }
        }
        return number;
    }
}
