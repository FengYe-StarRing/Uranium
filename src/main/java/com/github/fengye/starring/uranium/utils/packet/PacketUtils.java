package com.github.fengye.starring.uranium.utils.packet;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.game.AttackEvent;
import com.github.fengye.starring.uranium.listenable.module.impl.misc.Protocol;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.ProtocolUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;

public class PacketUtils extends MinecraftInstance {
    private static Packet<?> intervalPacket = null;

    public static void sendInterval() {
        if(intervalPacket == null) {
            return;
        }
        sendPacket(intervalPacket);
    }

    public static void attack(Entity entity,boolean event,boolean render) {
        if(event) {
            AttackEvent attackEvent = new AttackEvent(entity);
            Client.instance.eventManager.callEvent(attackEvent);
            if(attackEvent.isCancelled()) {
                return;
            }
        }
        C02PacketUseEntity c02 = new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK);
        ProtocolUtils.Versions fixToVersion = ProtocolUtils.fixToVersion();
        if(ProtocolUtils.thanV1_9(fixToVersion)) {
            // 1.9及以上
            sendPacket(c02);
            swingItem(render);
        } else {
            // 1.8及以下
            swingItem(render);
            sendPacket(c02);
        }
    }

    public static void attack(Entity entity) {
        attack(entity,true,true);
    }

    public static void swingItem(boolean render) {
        if(render) {
            mc.thePlayer.swingItem();
        } else {
            sendPacket(new C0APacketAnimation());
        }
    }
}
