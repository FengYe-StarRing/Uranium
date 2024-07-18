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
        return super.handleEvents();
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

    public static void switchItem(SwitchItemMode mode,boolean interval) {
        int slot = getSlot();
        int n;
        switch (mode) {
            case Switch:
                sendPacket(new C09PacketHeldItemChange(slot % 8 + 1));
                if(interval) {
                    sendInterval();
                }
                sendPacket(new C09PacketHeldItemChange(slot));
                break;
            case Change:
                sendPacket(new C09PacketHeldItemChange((slot % 8 + 2) % 8));
                if(interval) {
                    sendInterval();
                }
                sendPacket(new C09PacketHeldItemChange(slot));
                break;
            case SwitchAll:
                n = slot;
                for(int i = 0;i < 8;i++) {
                    n++;
                    if(n >= 8) {
                        n = 0;
                    }
                    sendPacket(new C09PacketHeldItemChange(n));
                    if(!(i == 7)) {
                        sendInterval();
                    }
                }
                break;
        }
    }

    public enum SwitchItemMode {
        Switch,Change,SwitchAll
    }
}
