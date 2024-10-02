package com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.impl;

import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.criticals.CriticalsMode;

public class PacketMode extends CriticalsMode {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Vanilla);

    @Override
    public void makeCrit() {
        switch ((Modes)modeValue.get()) {
            case Vanilla:
                sendCriticalPacket(0.0625,true);
                sendCriticalPacket(0.0);
                sendCriticalPacket(1.1E-5);
                sendCriticalPacket(0.0);
                break;
            case NCP:
                sendCriticalPacket(0.11);
                sendCriticalPacket(0.1100013579);
                sendCriticalPacket(0.0000013579);
                break;
        }
    }

    private enum Modes {
        Vanilla,NCP
    }
}
