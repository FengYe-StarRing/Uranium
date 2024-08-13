package com.github.fengye.starring.uranium.api.event.game.packet;

import com.github.fengye.starring.uranium.api.event.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent {
    private Packet<?> packet;
    private final PacketState state;

    public PacketEvent(Packet<?> packet,PacketState state) {
        super(new Class[]{PacketEvent.class,state.equals(PacketState.Send) ? PacketSendEvent.class : PacketRecieveEvent.class});
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
