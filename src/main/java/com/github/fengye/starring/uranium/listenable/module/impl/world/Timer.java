package com.github.fengye.starring.uranium.listenable.module.impl.world;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.impl.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.world.timer.TimerMode;
import com.github.fengye.starring.uranium.listenable.module.impl.world.timer.impl.*;

import java.util.List;

@ModuleInfo(name = "Timer", category = Category.World)
public class Timer extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Vanilla);
    public static final NumberValue timerSpeedValue = new NumberValue("TimerSpeed", 2, 0.05, 10, 0.05);

    @Override
    public void onEnable() {
        TimerMode mode = getMode();
        mode.setOldTimerSpeed(timer.timerSpeed);
        mode.onEnable();
    }

    @Override
    public void onDisable() {
        TimerMode mode = getMode();
        mode.setTimerSpeed(mode.getOldTimerSpeed());
        mode.onDisable();
    }

    private TimerMode getMode() {
        return Modes.valueOf(modeValue.getAsString()).getMode();
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        getMode().onUpdate(event);
    }

    @Override
    public void updateAddedValues() {
        updateAddedValues(Modes.values());
    }

    @Override
    public void updateValues(List<Value<?>> values) {
        updateValues(modeValue,values);
    }

    private enum Modes {
        Vanilla(new VanillaMode());

        private final TimerMode MODE;

        Modes(TimerMode mode) {
            MODE = mode;
        }

        public TimerMode getMode() {
            return MODE;
        }
    }
}
