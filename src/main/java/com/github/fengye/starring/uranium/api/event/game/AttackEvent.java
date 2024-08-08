package com.github.fengye.starring.uranium.api.event.game;

import com.github.fengye.starring.uranium.api.event.CancellableEvent;
import net.minecraft.entity.Entity;

public class AttackEvent extends CancellableEvent {
    private Entity target;

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public AttackEvent(Entity target) {
        this.target = target;
    }
}
