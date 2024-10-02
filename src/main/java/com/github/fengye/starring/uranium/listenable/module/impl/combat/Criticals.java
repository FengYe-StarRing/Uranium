package com.github.fengye.starring.uranium.listenable.module.impl.combat;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.AttackEvent;
import com.github.fengye.starring.uranium.api.event.game.motion.MotionEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.CriticalsMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl.HopMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl.JumpMode;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl.PacketMode;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.entity.Entity;

import java.util.List;

@ModuleInfo(name = "Criticals",category = Category.Combat)
public class Criticals extends Module {
    private final ModeValue timingValue = new ModeValue("Timing",TimingModes.values(),TimingModes.Attack);
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Packet);
    public static final ModeValue critPacketModeValue = new ModeValue("CritPacketMode", PacketModes.values(),PacketModes.C04);
    private final NumberValue critParticleNumberValue = new NumberValue("CritParticleNumber",0,0,20,1);
    private final NumberValue enchantedParticleNumber = new NumberValue("EnchantedParticleNumber",1,0,20,1);
    private final NumberValue delayValue = new NumberValue("Delay",0,0,1000,1);

    private final Timer delayTimer = new Timer();

    @Override
    public void onEnable() {
        delayTimer.reset();
    }

    private CriticalsMode getMode() {
        return Modes.valueOf(modeValue.getAsString()).getMode();
    }

    @EventHandle
    private void onAttack(AttackEvent event) {
        if(timingValue.get().equals(TimingModes.Attack) || timingValue.get().equals(TimingModes.All)) {
            makeCrit(event.getTarget());
        }
    }

    @EventHandle
    private void onMotion(MotionEvent event) {
        if(event.isPre()) {
            if(timingValue.get().equals(TimingModes.Pre) || timingValue.get().equals(TimingModes.All)) {
                makeCrit(null);
            }
        }
        if(event.isPost()) {
            if(timingValue.get().equals(TimingModes.Post) || timingValue.get().equals(TimingModes.All)) {
                makeCrit(null);
            }
        }
        if(timingValue.get().equals(TimingModes.Motion) || timingValue.get().equals(TimingModes.All)) {
            makeCrit(null);
        }
    }

    private boolean canCrit() {
        return thePlayer.onGround && delayTimer.hasTimePassed(delayValue.get().longValue()) && (timingValue.get().equals(TimingModes.Attack) || KillAura.targetActive());
    }

    private void makeCrit(Entity target) {
        if(canCrit()) {
            if(target == null) {
                target = KillAura.getTarget();
            }
            getMode().makeCrit();
            for(int i = 0; i < critParticleNumberValue.get(); i++) {
                thePlayer.onCriticalHit(target);
            }
            for(int i = 0; i < enchantedParticleNumber.get(); i++) {
                thePlayer.onEnchantmentCritical(target);
            }
            delayTimer.reset();
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

    @Override
    public String getTag() {
        return modeValue.getAsString();
    }

    private enum Modes {
        Packet(new PacketMode()),
        Fake(new CriticalsMode()),
        Jump(new JumpMode()),
        Hop(new HopMode());

        private final CriticalsMode MODE;

        Modes(CriticalsMode mode) {
            MODE = mode;
        }

        public CriticalsMode getMode() {
            return MODE;
        }
    }

    private enum TimingModes {
        Attack,All,Pre,Post,Motion
    }

    public enum PacketModes {
        C04,C06
    }
}
