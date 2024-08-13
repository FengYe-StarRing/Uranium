package com.github.fengye.starring.uranium.utils.entity;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.module.impl.misc.Targeting;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils extends MinecraftInstance{
    private static Targeting targeting;

    public static List<Entity> getTargetEntities(List<Entity> entities) {
        List<Entity> targets = new ArrayList<>();
        for (Entity entity : entities) {
            if(isTargetEntity(entity)) {
                targets.add(entity);
            }
        }
        return targets;
    }

    public static List<Entity> getTargetEntities() {
        return getTargetEntities(theWorld.loadedEntityList);
    }

    public static boolean isTargetEntity(Entity entity) {
        if(targeting == null) {
            targeting = (Targeting) Client.instance.moduleManager.getModule(Targeting.class);
        }
        if(!targeting.isEnabled()) {
            return true;
        }
        if(entity.getEntityId() == thePlayer.getEntityId()) {
            return false;
        }
        boolean invisible = Targeting.invisible.get() && entity.isInvisible();
        boolean player = Targeting.player.get() && entity instanceof EntityPlayer;
        boolean mobs = Targeting.mob.get() && entity instanceof EntityMob;
        boolean animals = Targeting.animals.get() && entity instanceof EntityAnimal;
        boolean friend;
        boolean team;
        boolean villager = Targeting.villager.get() && entity instanceof EntityVillager;
        boolean fake;
        boolean ironGolem = Targeting.ironGolem.get() && entity instanceof EntityIronGolem;
        return invisible || player || mobs || animals || villager || ironGolem;
    }

    public static List<EntityLivingBase> getLivingBaseEntities(List<Entity> entities) {
        List<EntityLivingBase> livingBases = new ArrayList<>();
        for (Entity entity : entities) {
            if(entity instanceof EntityLivingBase) {
                livingBases.add((EntityLivingBase) entity);
            }
        }
        return livingBases;
    }

    public static List<EntityLivingBase> getLivingBaseEntities() {
        return getLivingBaseEntities(MinecraftInstance.theWorld.loadedEntityList);
    }

    public static List<Entity> getEntitiesInRange(Entity entity,double range) {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity1 : theWorld.loadedEntityList) {
            if(entity.getEntityId() != entity1.getEntityId() && entity.getDistanceToEntity(entity1) <= range) {
                entities.add(entity1);
            }
        }
        return entities;
    }

    public static List<Entity> getEntitiesInRange(double range) {
        return getEntitiesInRange(thePlayer,range);
    }

    public static boolean isLivingEntity(EntityLivingBase entity) {
        return entity.getHealth() > 0 && !entity.isDead;
    }
}
