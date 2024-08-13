package com.github.fengye.starring.uranium.listenable.module.impl.misc;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.TickEvent;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.manager.impl.NotificationManager;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S2FPacketSetSlot;

@ModuleInfo(name = "DebugTool",category = Category.Misc)
public class DebugTool extends Module {
    // Event
    private final OptionValue tickValue = new OptionValue("Tick",false);
    private final OptionValue updateValue = new OptionValue("Update",false);
    // Packet
    private final OptionValue cpsValue = new OptionValue("CPS",false);
    private final OptionValue cPacketValue = new OptionValue("CPacket",false);
    private final OptionValue c03Value = new OptionValue("C03",false);
    private final OptionValue c0bValue = new OptionValue("C0B",false);
    private final OptionValue sPacketValue = new OptionValue("SPacket",false);
    private final OptionValue s2fValue = new OptionValue("S2F",false);

    private int tick = 0;
    private final Timer tickTimer = new Timer();
    private int update = 0;
    private final Timer updateTimer = new Timer();
    private int cps = 0;
    private final Timer cpsTimer = new Timer();
    private int cPacket = 0;
    private final Timer cPacketTimer = new Timer();
    private int sPacket = 0;
    private final Timer sPacketTimer = new Timer();
    private int s2f = 0;
    private final Timer s2fTimer = new Timer();
    private int c03 = 0;
    private final Timer c03Timer = new Timer();
    private int c0b = 0;
    private final Timer c0bTimer = new Timer();

    @Override
    public void onEnable() {
        tick = 0;
        tickTimer.reset();
        update = 0;
        updateTimer.reset();
        cps = 0;
        cpsTimer.reset();
        cPacket = 0;
        cPacketTimer.reset();
        sPacket = 0;
        sPacketTimer.reset();
        s2f = 0;
        s2fTimer.reset();
        c03 = 0;
        c03Timer.reset();
        c0b = 0;
        c0bTimer.reset();
    }

    @EventHandle
    private void onTick(TickEvent event) {
        if(tickValue.get()) {
            tick++;
            if(tick == 1) {
                tickTimer.reset();
            }
        }
    }

    private void post(String title,String[] text) {
        NotificationManager.post(title,text, NotificationManager.NotifType.None,0.5);
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        if(updateValue.get()) {
            update++;
            if(update == 1) {
                updateTimer.reset();
            }
        }
    }

    @EventHandle(priority = Priority.LOW)
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(event.isCancelled()) {
            return;
        }
        if(packet instanceof C0APacketAnimation) {
            if(cpsValue.get()) {
                cps++;
                if(cps == 1) {
                    cpsTimer.reset();
                }
            }
        }
        if(cPacketValue.get()) {
            cPacket++;
            if(cPacket == 1) {
                cPacketTimer.reset();
            }
        }
        if(packet instanceof C03PacketPlayer) {
            if(c03Value.get()) {
                c03++;
                if(c03 == 1) {
                    c03Timer.reset();
                }
            }
        }
        if(packet instanceof C0BPacketEntityAction) {
            if(c0bValue.get()) {
                c0b++;
                if(c0b == 1) {
                    c0bTimer.reset();
                }
            }
        }
    }

    @EventHandle
    private void onEvent(Event event) {
        if(renderFrequency(tick,tickTimer,"Tick Event")) {
            tick = 0;
        }
        if(renderFrequency(update,updateTimer,"Update Event")) {
            update = 0;
        }
        if(renderFrequency(cps,cpsTimer,"CPS")) {
            cps = 0;
        }
        if(renderFrequency(cPacket,cPacketTimer,"Client Packet")) {
            cPacket = 0;
        }
        if(renderFrequency(sPacket,sPacketTimer,"Server Packet")) {
            sPacket = 0;
        }
        if(renderFrequency(s2f,s2fTimer,"S2F Packet")) {
            s2f = 0;
        }
        if(renderFrequency(c03,c03Timer,"C03 Packet")) {
            c03 = 0;
        }
        if(renderFrequency(c0b,c0bTimer,"C0B Packet")) {
            c0b = 0;
        }
    }

    private boolean renderFrequency(int count,Timer timer,String render) {
        if(count == 0) {
            return false;
        }
        if(timer.hasTimePassed(1000)) {
            timer.reset();
            post(getName(),new String[]{render,count + "/s"});
            return true;
        }
        return false;
    }

    @EventHandle(priority = Priority.LOW)
    private void onPacketRecieve(PacketRecieveEvent event) {
        Packet<?> packet = event.getPacket();
        if(event.isCancelled()) {
            return;
        }
        if(sPacketValue.get()) {
            sPacket++;
            if(sPacket == 1) {
                sPacketTimer.reset();
            }
        }
        if(packet instanceof S2FPacketSetSlot) {
            if(s2fValue.get()) {
                s2f++;
                if(s2f == 1) {
                    s2fTimer.reset();
                }
            }
        }
    }

//    @EventHandle
//    private void onPacket(PacketEvent event) {
//        System.out.println(event.getPacket());
//    }
}
