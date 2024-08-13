package com.github.fengye.starring.uranium.listenable.module.impl.combat;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.rotation.RotationPreEvent;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.utils.entity.EntityUtils;
import com.github.fengye.starring.uranium.utils.packet.PacketUtils;
import com.github.fengye.starring.uranium.utils.rotation.Rotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "KillAura",category = Category.Combat)
public class KillAura extends Module {
    private final List<EntityLivingBase> targets = new ArrayList<>();
    private static EntityLivingBase target;
    private Rotation rotation;

    @Override
    public void onEnable() {
        targets.clear();
        target = null;
        rotation = null;
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        updateTarget();
        runAttack();
    }

    private void updateTarget() {
        targets.clear();
        targets.addAll(getTargets());
        if(!targets.isEmpty()) {
            target = targets.get(0);
        } else {
            target = null;
        }
    }

    private double getRange() {
        return 8;
    }

    public static EntityLivingBase getTarget() {
        return target;
    }

    private List<EntityLivingBase> getTargets() {
        List<Entity> inRangeEntities = EntityUtils.getEntitiesInRange(getRange());
        List<EntityLivingBase> targets = new ArrayList<>();
        for (Entity inRangeEntity : inRangeEntities) {
            if(inRangeEntity instanceof EntityLivingBase && EntityUtils.isLivingEntity((EntityLivingBase) inRangeEntity) && EntityUtils.isTargetEntity(inRangeEntity)) {
                targets.add((EntityLivingBase) inRangeEntity);
            }
        }
        return targets;
    }

    private void runAttack() {
        if(canAttack()) {
            attack();
        }
    }

    private void attack() {
        PacketUtils.attack(target);
    }

    private boolean canAttack() {
        return targetActive();
    }

    private void updateRotation() {
//        rotation = RotationUtils.getRotationEntity(target);
    }

    public static boolean targetActive() {
        return target != null;
    }

    @EventHandle
    private void onRotationPre(RotationPreEvent event) {
        if(targetActive()) {
            updateRotation();
            event.setRotation(rotation);
        }
    }
}
