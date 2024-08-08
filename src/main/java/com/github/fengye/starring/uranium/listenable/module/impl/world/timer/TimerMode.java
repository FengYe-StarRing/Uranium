package com.github.fengye.starring.uranium.listenable.module.impl.world.timer;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.module.ModuleInMode;
import com.github.fengye.starring.uranium.listenable.module.impl.world.Timer;

public class TimerMode extends ModuleInMode {
    private static Timer timerModule;
    private float oldTimerSpeed = 1F;

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

    protected void toggle() {
        if(timerModule == null) {
            timerModule = (Timer) Client.instance.moduleManager.getModule(Timer.class);
        }
        timerModule.setEnabled();
    }

    public String getTag() {
        return null;
    }
}
