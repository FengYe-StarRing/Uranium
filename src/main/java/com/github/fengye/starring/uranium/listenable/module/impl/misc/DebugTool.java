package com.github.fengye.starring.uranium.listenable.module.impl.misc;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketSendEvent;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.manager.impl.NotificationManager;
import com.github.fengye.starring.uranium.utils.packet.PacketUtils;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;

@ModuleInfo(name = "DebugTool",category = Category.Misc)
public class DebugTool extends Module {
    // Frequency
    private final OptionValue frequencySendCPacketValue = new OptionValue("Frequency-SendCPacket",false);
    private final OptionValue frequencyRecieveCPacketValue = new OptionValue("Frequency-RecieveCPacket",false);
    private final OptionValue frequencySendC0AValue = new OptionValue("Frequency-SendC0A",false);

    private final Timer frequencySendCPacketTimer = new Timer();
    private int frequencySendCPacket = 0;
    private final Timer frequencyRecieveCPacketTimer = new Timer();
    private int frequencyRecieveCPacket = 0;
    private final Timer frequencySendC0ATimer = new Timer();
    private int frequencySendC0A = 0;

    @Override
    public void onEnable() {
        frequencySendCPacketTimer.reset();
        frequencySendCPacket = 0;
        frequencyRecieveCPacketTimer.reset();
        frequencyRecieveCPacket = 0;
        frequencySendC0ATimer.reset();
        frequencySendC0A = 0;
    }

    @EventHandle(priority = Priority.LOW)
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if(frequencySendCPacketValue.get()) {
            frequencySendCPacket++;
            if(frequencySendCPacket == 1) {
                frequencySendCPacketTimer.reset();
            }
        }
        if(packet instanceof C0APacketAnimation) {
            if(frequencySendC0AValue.get()) {
                frequencySendC0A++;
                if(frequencySendC0A == 1) {
                    frequencySendC0ATimer.reset();
                }
            }
        }
    }

    @EventHandle
    private void onEvent(Event event) {
        if(renderFrequency(frequencySendCPacket,frequencySendCPacketTimer,"Send Client Packet")) {
            frequencySendCPacket = 0;
        }
        if(renderFrequency(frequencyRecieveCPacket,frequencyRecieveCPacketTimer,"Recieve Client Packet")) {
            frequencyRecieveCPacket = 0;
        }
        if(renderFrequency(frequencySendC0A,frequencySendC0ATimer,"Send C0A Packet")) {
            frequencySendC0A = 0;
        }
    }

    private boolean renderFrequency(int number,Timer timer,String title) {
        if(number == 0) {
            return false;
        }
        if(timer.hasTimePassed(1000)) {
            NotificationManager.post(title,new String[]{"Speed: " + number + "/s"}, NotificationManager.NotifType.None,0.5);
            timer.reset();
            return true;
        }
        return false;
    }

    @EventHandle(priority = Priority.LOW)
    private void onPacketRecieve(PacketRecieveEvent event) {
        Packet<?> packet = event.getPacket();
        if(PacketUtils.isCPacket(packet)) {
            if(frequencyRecieveCPacketValue.get()) {
                frequencyRecieveCPacket++;
                if(frequencyRecieveCPacket == 1) {
                    frequencyRecieveCPacketTimer.reset();
                }
            }
        }
    }
}
