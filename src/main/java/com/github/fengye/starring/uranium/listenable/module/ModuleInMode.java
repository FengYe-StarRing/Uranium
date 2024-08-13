package com.github.fengye.starring.uranium.listenable.module;

import com.github.fengye.starring.uranium.api.event.game.AttackEvent;
import com.github.fengye.starring.uranium.api.event.game.TickEvent;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.motion.MotionEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.util.List;

public abstract class ModuleInMode extends MinecraftInstance {
    public List<Value<?>> getValues() {
        return JavaUtils.getValues(this);
    }

    public void onMotion(MotionEvent event) {

    }

    public void onPacketSend(PacketSendEvent event) {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onPacketRecieve(PacketRecieveEvent event) {

    }

    public void onUpdate(UpdateEvent event) {

    }

    public void onAttack(AttackEvent event) {

    }

    public void updateValues() {

    }

    public String getTag() {
        return null;
    }

    public void onTick(TickEvent event) {

    }
}
