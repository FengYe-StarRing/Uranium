package com.github.fengye.starring.uranium.listenable.module.impl.world.timer.impl;

import com.github.fengye.starring.uranium.api.event.impl.UpdateEvent;
import com.github.fengye.starring.uranium.listenable.module.impl.world.timer.TimerMode;

public class VanillaMode extends TimerMode {
    @Override
    public void onUpdate(UpdateEvent event) {
        setTimerSpeed(getTargetTimerSpeed());
    }
}
