package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.impl.packet.PacketSendEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class C09Utils extends PacketUtils implements Listenable {
    private static int slot = -1;

    @Override
    public boolean handleEvents() {
        return true;
    }

    public static int getSlot() {
        if(slot == -1) {
            slot = thePlayer.inventory.currentItem;
        }
        return slot;
    }

    public static ItemStack getHeldItem() {
        return thePlayer.inventory.getStackInSlot(getSlot());
    }

    @EventHandle
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet == null || event.isCancelled()) {
            return;
        }
        if(packet instanceof C09PacketHeldItemChange) {
            slot = ((C09PacketHeldItemChange) packet).getSlotId();
        }
    }
}
