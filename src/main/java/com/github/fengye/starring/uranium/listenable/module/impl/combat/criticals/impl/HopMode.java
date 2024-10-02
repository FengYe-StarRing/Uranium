package com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl;

import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.CriticalsMode;

public class HopMode extends CriticalsMode {
    @Override
    public void makeCrit() {
        thePlayer.motionY = 0.1;
    }
}
