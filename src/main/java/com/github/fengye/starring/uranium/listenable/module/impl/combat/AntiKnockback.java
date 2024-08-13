package com.github.fengye.starring.uranium.listenable.module.impl.combat;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.AntiKnockbackMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.impl.LegitMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.impl.SimpleMode;
import com.github.fengye.starring.uranium.utils.timer.Timer;

import java.util.List;

@ModuleInfo(name = "AntiKnockback",category = Category.Combat)
public class AntiKnockback extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Simple);
    public final NumberValue delayValue = new NumberValue("Delay",0,0,200,1);

    public final Timer delayTimer = new Timer();

    @Override
    public void onEnable() {
        delayTimer.reset();
    }

    private AntiKnockbackMode getMode() {
        return Modes.valueOf(modeValue.getAsString()).getMode();
    }

    @Override
    public void updateAddedValues() {
        updateAddedValues(Modes.values());
    }

    @Override
    public void updateValues(List<Value<?>> values) {
        updateValues(modeValue,values);
        getMode().updateValues();
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

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        getMode().onUpdate(event);
    }

    private enum Modes {
        Simple(new SimpleMode()),
        Legit(new LegitMode());

        private final AntiKnockbackMode MODE;

        Modes(AntiKnockbackMode mode) {
            MODE = mode;
        }

        public AntiKnockbackMode getMode() {
            return MODE;
        }
    }
}
