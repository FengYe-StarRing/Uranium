package com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl;

import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.CriticalsMode;

public class PacketMode extends CriticalsMode {
    @Override
    public void makeCrit() {
        sendCriticalPacket(0.0625,true);
        sendCriticalPacket(0.0);
        sendCriticalPacket(1.1E-5);
        sendCriticalPacket(0.0);
    }
}
