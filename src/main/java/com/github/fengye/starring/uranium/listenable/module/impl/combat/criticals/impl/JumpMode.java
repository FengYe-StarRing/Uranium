package com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl;

import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.CriticalsMode;

public class JumpMode extends CriticalsMode {
    private final OptionValue lowValue = new OptionValue("Low",false);

    @Override
    public void makeCrit() {
        if(lowValue.get()) {
            thePlayer.motionY = 0.3425;
        } else {
            thePlayer.jump();
        }
    }
}
