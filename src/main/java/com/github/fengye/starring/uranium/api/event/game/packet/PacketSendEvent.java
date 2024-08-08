package com.github.fengye.starring.uranium.api.event.game.packet;

import net.minecraft.network.Packet;

public class PacketSendEvent extends PacketEvent {
    public PacketSendEvent(Packet<?> packet) {
        super(packet,PacketState.Send);
    }
}
