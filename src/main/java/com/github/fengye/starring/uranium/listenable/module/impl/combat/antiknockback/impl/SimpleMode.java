package com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.impl;

import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.AntiKnockbackMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class SimpleMode extends AntiKnockbackMode {
    private final NumberValue horizontalValue = new NumberValue("Horizontal",0,0,1,0.01);
    private final NumberValue verticalValue = new NumberValue("Vertical",0,0,1,0.01);
    private final OptionValue cancelValue = new OptionValue("Cancel",false);

    @Override
    public void onPacketRecieve(PacketRecieveEvent event) {
        Packet<?> packet = event.getPacket();
        if(thePlayer == null || !canReduce()) {
            return;
        }
        int id = thePlayer.getEntityId();
        if(packet instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) packet;
            if(s12.getEntityID() == id) {
                if(cancelValue.get()) {
                    event.cancelEvent();
                } else {
                    s12.motionX *= horizontalValue.get();
                    s12.motionY *= verticalValue.get();
                    s12.motionZ *= horizontalValue.get();
                }
                resetDelay();
            }
        }
        if(packet instanceof S27PacketExplosion) {
            S27PacketExplosion s27 = (S27PacketExplosion) packet;
            if(cancelValue.get()) {
                event.cancelEvent();
            } else {
                s27.field_149152_f *= horizontalValue.get();
                s27.field_149153_g *= verticalValue.get();
                s27.field_149159_h *= horizontalValue.get();
            }
            resetDelay();
        }
    }

    @Override
    public void updateValues() {
        if(cancelValue.get()) {
            horizontalValue.setDisplay(false);
            verticalValue.setDisplay(false);
        } else {
            horizontalValue.setDisplay(true);
            verticalValue.setDisplay(true);
        }
    }

    @Override
    public String getTag() {
        return cancelValue.get() ? null : ((int)(horizontalValue.get() * 100)) + "%," + ((int)(verticalValue.get() * 100)) + "%";
    }
}
