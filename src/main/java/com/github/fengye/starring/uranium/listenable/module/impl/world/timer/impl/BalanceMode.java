package com.github.fengye.starring.uranium.listenable.module.impl.world.timer.impl;

import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.impl.world.timer.TimerMode;
import com.github.fengye.starring.uranium.utils.entity.MovementUtils;
import com.github.fengye.starring.uranium.utils.packet.BlinkUtils;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

import java.util.ArrayList;
import java.util.List;

public class BalanceMode extends TimerMode {
    private final OptionValue autoShutOffValue = new OptionValue("AutoShutOff",true);
    private final NumberValue maxTimeValue = new NumberValue("MaxTime",0,0,110,1);

    private final Timer balanceTimeTimer = new Timer();
    private final List<Integer> packets = new ArrayList<>();
    private int balance = 0;

    @Override
    public void onEnable() {
        balance = 0;
        balanceTimeTimer.reset();
        packets.clear();
    }

    @Override
    public void onDisable() {
        BlinkUtils.blink(packets);
    }

    @Override
    public void onPacketRecieve(PacketRecieveEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet instanceof S32PacketConfirmTransaction) {
            packets.add(BlinkUtils.collect(event));
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if(canBalanced()) {
            long time = (long) (maxTimeValue.get() * 1000);
            if(time > 0) {
                if(balanceTimeTimer.hasTimePassed(time)) {
                    balanceEnd();
                }
            }
        } else {
            balanceTimeTimer.reset();
        }
        if(inBalanceMoving()) {
            setTimerSpeed();
        } else {
            if(MovementUtils.isMoving() && canBalanced() && balance <= 0) {
                balanceEnd();
            }
            setOldTimerSpeed();
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet instanceof C03PacketPlayer) {
            C03PacketPlayer c03 = (C03PacketPlayer) packet;
            if(canBalanced()) {
                if(inBalanceMoving() && !event.isCancelled()) {
                    balance--;
                } else {
                    if(!c03.getRotating() && !c03.isMoving()) {
                        balance++;
                        event.cancelEvent();
                    }
                }
            }
        }
    }

    private boolean inBalanceMoving() {
        return canBalanced() && MovementUtils.isMoving() && balance > 0;
    }

    private void  balanceEnd() {
        BlinkUtils.blink(packets);
        if(autoShutOffValue.get()) {
            toggle();
        } else {
            setOldTimerSpeed();
        }
    }

    private boolean canBalanced() {
        return !packets.isEmpty();
    }
}
