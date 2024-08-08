package com.github.fengye.starring.uranium.api.event.game.packet;

import net.minecraft.network.Packet;

public class PacketRecieveEvent extends PacketEvent {
    public PacketRecieveEvent(Packet<?> packet) {
        super(packet,PacketState.Recieve);
    }
}
