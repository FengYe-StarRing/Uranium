package com.github.fengye.starring.uranium.api.event.impl.packet;

import net.minecraft.network.Packet;

public class PacketRecieveEvent extends PacketEvent {
    public PacketRecieveEvent(Packet<?> packet) {
        super(packet);
    }
}
