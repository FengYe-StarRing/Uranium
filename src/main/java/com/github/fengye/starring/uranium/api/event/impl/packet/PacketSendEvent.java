package com.github.fengye.starring.uranium.api.event.impl.packet;

import net.minecraft.network.Packet;

public class PacketSendEvent extends PacketEvent {
    public PacketSendEvent(Packet<?> packet) {
        super(packet,PacketState.Send);
    }
}
