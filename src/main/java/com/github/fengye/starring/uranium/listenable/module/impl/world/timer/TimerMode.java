package com.github.fengye.starring.uranium.listenable.module.impl.world.timer;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.impl.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.impl.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.impl.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.listenable.module.ModuleInMode;
import com.github.fengye.starring.uranium.listenable.module.impl.world.Timer;

public class TimerMode extends ModuleInMode {
    private static Timer timerModule;
    private float oldTimerSpeed = 1F;

    public void onUpdate(UpdateEvent event) {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void setOldTimerSpeed(float oldTimerSpeed) {
        this.oldTimerSpeed = oldTimerSpeed;
    }

    public void setOldTimerSpeed() {
        setTimerSpeed(oldTimerSpeed);
    }

    public float getOldTimerSpeed() {
        return oldTimerSpeed;
    }

    public void setTimerSpeed(float speed) {
        timer.timerSpeed = speed;
    }

    public void setTimerSpeed() {
        setTimerSpeed(getTargetTimerSpeed());
    }

    public float getTargetTimerSpeed() {
        return Timer.speedValue.get().floatValue();
    }

    public void onPacketSend(PacketSendEvent event) {

    }

    public void onPacketRecieve(PacketRecieveEvent event) {

    }

    protected void toggle() {
        if(timerModule == null) {
            timerModule = (Timer) Client.instance.moduleManager.getModule(Timer.class);
        }
        timerModule.setEnabled();
    }
}
