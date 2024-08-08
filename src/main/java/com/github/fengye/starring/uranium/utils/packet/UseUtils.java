package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class UseUtils extends PacketUtils implements Listenable {
    private static boolean blocking = false;

    @EventHandle(priority = Priority.MINIMUM)
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet == null || event.isCancelled()) {
            return;
        }
        if(packet instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement c08 = (C08PacketPlayerBlockPlacement) packet;
            if(isBlockPacket(c08)) {
                blocking = true;
            }
        }
        if(packet instanceof C07PacketPlayerDigging) {
            C07PacketPlayerDigging c07 = (C07PacketPlayerDigging) packet;
            if(isCancelBlockPacket(c07)) {
                blocking = false;
            }
        }
    }

    public static boolean isBlockPacket(C08PacketPlayerBlockPlacement c08) {
        ItemStack stack = c08.getStack();
        if(stack != null && stack.getItem() instanceof ItemSword) {
            return c08.getPlacedBlockDirection() == 255;
        }
        return false;
    }

    public static boolean isCancelBlockPacket(C07PacketPlayerDigging c07) {
        return c07.getStatus().equals(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM);
    }

    public static boolean isBlocking() {
        return blocking;
    }

    public static boolean isUsingItem() {
        return isBlocking() || isUseConsume();
    }

    public static boolean isUseConsume() {
        return thePlayer.getItemInUse() != null;
    }

    private static void updateState() {
        if(thePlayer == null) {
            return;
        }
        ItemStack stack = thePlayer.getHeldItem();
        if(stack == null || stack.getItem() == null) {
            blocking = false;
            return;
        }
        blocking = isBlocking() && stack.getItem() instanceof ItemSword;
    }

    @EventHandle(priority = Priority.MINIMUM)
    private void onEvent(Event event) {
        updateState();
    }
}
