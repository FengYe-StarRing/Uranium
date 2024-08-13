package com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.impl;

import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.antiknockback.AntiKnockbackMode;
import com.github.fengye.starring.uranium.utils.entity.MovementUtils;
import com.github.fengye.starring.uranium.utils.packet.C0BUtils;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class LegitMode extends AntiKnockbackMode {
    private final OptionValue jumpValue = new OptionValue("Jump",true);
    private final OptionValue sprintResetValue = new OptionValue("SprintReset",true);

    @Override
    public void onUpdate(UpdateEvent event) {
        if(thePlayer.hurtResistantTime == 19 && canReduce()) {
            if(sprintResetValue.get() && C0BUtils.canSprint() && C0BUtils.isSprinting()) {
                C0BUtils.sprintReset();
            }
            if(jumpValue.get() && thePlayer.onGround && MovementUtils.isMoving() && movementInput.moveForward > 0) {
                thePlayer.jump();
            }
        }
    }
}
