package com.github.fengye.starring.uranium.listenable.module.impl.world;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.world.timer.TimerMode;
import com.github.fengye.starring.uranium.listenable.module.impl.world.timer.impl.*;
import com.github.fengye.starring.uranium.utils.entity.MovementUtils;

import java.util.List;

@ModuleInfo(name = "Timer", category = Category.World)
public class Timer extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Vanilla);
    public static final NumberValue speedValue = new NumberValue("Speed", 4, 0.05, 10, 0.05);
    private final OptionValue onlyMoveValue = new OptionValue("OnlyMove",false);
    private final OptionValue inGroundValue = new OptionValue("InGround",true);
    private final OptionValue inAirValue = new OptionValue("InAir",true);
    private final OptionValue inFallDownValue = new OptionValue("InFallDown",true);

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
        if(onlyMoveValue.get() && !MovementUtils.isMoving()) {
            getMode().setOldTimerSpeed();
        }
        boolean ground = thePlayer.onGround;
        if(!((inGroundValue.get() && ground) || (inAirValue.get() && !ground) || (inFallDownValue.get() && thePlayer.fallDistance > 0))) {
            getMode().setOldTimerSpeed();
        }
    }

    @Override
    public void updateAddedValues() {
        updateAddedValues(Modes.values());
    }

    @Override
    public void updateValues(List<Value<?>> values) {
        updateValues(modeValue,values);
    }

    @EventHandle
    private void onPacketSend(PacketSendEvent event) {
        getMode().onPacketSend(event);
    }

    @EventHandle
    private void onPacketRecieve(PacketRecieveEvent event) {
        getMode().onPacketRecieve(event);
    }

    @Override
    public String getTag() {
        String tag = getMode().getTag();
        return tag == null || tag.isEmpty() ? modeValue.getAsString() : tag;
    }

    private enum Modes {
        Vanilla(new VanillaMode()),
        Balance(new BalanceMode());

        private final TimerMode MODE;

        Modes(TimerMode mode) {
            MODE = mode;
        }

        public TimerMode getMode() {
            return MODE;
        }
    }
}
