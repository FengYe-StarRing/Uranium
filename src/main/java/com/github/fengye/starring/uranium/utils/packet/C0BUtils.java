package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class C0BUtils extends PacketUtils implements Listenable {
    private static boolean sneaking = false;
    private static boolean sprinting = false;

    @EventHandle(priority = Priority.HIGHEST)
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(event.isCancelled()) {
            return;
        }
        if(packet instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction c0b = (C0BPacketEntityAction) packet;
            if(c0b.entityID == thePlayer.getEntityId()) {
                switch (c0b.getAction()) {
                    case START_SNEAKING:
                        sneaking = true;
                        break;
                    case STOP_SNEAKING:
                        sneaking = false;
                        break;
                    case START_SPRINTING:
                        sprinting = true;
                        break;
                    case STOP_SPRINTING:
                        sprinting = false;
                        break;
                }
            }
        }
    }

    public static boolean isSneaking() {
        return sneaking;
    }

    public static boolean isSprinting() {
        return sprinting;
    }

    public static boolean canSprint() {
        return PositionUtils.isMoving() && movementInput.moveForward >= 0.8;
    }

    public static void sprintReset() {
        if(sprinting) {
            sendPacket(new C0BPacketEntityAction(thePlayer,C0BPacketEntityAction.Action.STOP_SPRINTING));
            sendPacket(new C0BPacketEntityAction(thePlayer,C0BPacketEntityAction.Action.START_SPRINTING));
        } else {
            sendPacket(new C0BPacketEntityAction(thePlayer,C0BPacketEntityAction.Action.START_SPRINTING));
            sendPacket(new C0BPacketEntityAction(thePlayer,C0BPacketEntityAction.Action.STOP_SPRINTING));
        }
    }
}
