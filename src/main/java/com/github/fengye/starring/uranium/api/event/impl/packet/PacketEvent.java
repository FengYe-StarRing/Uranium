package com.github.fengye.starring.uranium.api.event.impl.packet;

import com.github.fengye.starring.uranium.api.event.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent {
    private Packet<?> packet;
    private final PacketState state;

    public PacketEvent(Packet<?> packet,PacketState state) {
        this.packet = packet;
        this.state = state;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public PacketState getState() {
        return state;
    }
}
