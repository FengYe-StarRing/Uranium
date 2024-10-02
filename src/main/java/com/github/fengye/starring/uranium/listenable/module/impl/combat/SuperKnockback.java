package com.github.fengye.starring.uranium.listenable.module.impl.combat;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.AttackEvent;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.utils.packet.C0BUtils;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleInfo(name = "SuperKnockback",category = Category.Combat)
public class SuperKnockback extends Module {
    private final ModeValue timingValue = new ModeValue("Timing",TimingModes.values(),TimingModes.Attack);
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Legit);
    private final NumberValue delayValue = new NumberValue("Delay",0,0,1000,1);

    private final Timer delayTimer = new Timer();

    @Override
    public void onEnable() {
        delayTimer.reset();
    }

    @EventHandle
    private void onAttack(AttackEvent event) {
        if(timingValue.get().equals(TimingModes.Attack)) {
            makeKB();
        }
    }

    private void makeKB() {
        if(canKB()) {
            switch ((Modes)modeValue.get()) {
                case Legit:
                    if(C0BUtils.isSprinting()) {
                        C0BUtils.sprintReset();
                    }
                    break;
                case Packet:
                    sendPacket(new C0BPacketEntityAction(thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    break;
            }
            delayTimer.reset();
        }
    }

    private boolean canKB() {
        return C0BUtils.canSprint() && delayTimer.hasTimePassed(delayValue.get().longValue()) && (timingValue.get().equals(TimingModes.Attack) || KillAura.targetActive());
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        if(timingValue.get().equals(TimingModes.Update)) {
            makeKB();
        }
    }

    private enum Modes {
        Legit,Packet
    }

    private enum TimingModes {
        Attack,Update
    }
}
