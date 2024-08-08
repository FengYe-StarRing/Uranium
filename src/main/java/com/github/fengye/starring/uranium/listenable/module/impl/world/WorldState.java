package com.github.fengye.starring.uranium.listenable.module.impl.world;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.event.game.packet.PacketRecieveEvent;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.world.storage.WorldInfo;

@ModuleInfo(name = "WorldState", category = Category.World)
public class WorldState extends Module {
    private final NumberValue timeValue = new NumberValue("Time",18000,0,24000,1);
    private final OptionValue changeTimeValue = new OptionValue("ChangeTime",true);
    private final ModeValue weatherValue = new ModeValue("Weather",WeatherModes.values(),WeatherModes.Thunder);
    private final OptionValue changeWeatherValue = new OptionValue("ChangeWeather",true);


    @EventHandle
    private void onPacketRecieve(PacketRecieveEvent event) {
        Packet<?> packet = event.getPacket();
        if(event.isCancelled() || packet == null) {
            return;
        }
        if(packet instanceof S03PacketTimeUpdate) {
            S03PacketTimeUpdate s03 = (S03PacketTimeUpdate) packet;
            if(changeTimeValue.get()) {
                s03.setWorldTime(timeValue.get().longValue());
            }
        }
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        if(serverWorld == null) {
            return;
        }
        WorldInfo worldInfo = serverWorld.getWorldInfo();
        if(changeWeatherValue.get()) {
            int i = 100;
            switch ((WeatherModes)weatherValue.get()) {
                case Clear:
                    worldInfo.setCleanWeatherTime(i);
                    worldInfo.setRainTime(0);
                    worldInfo.setThunderTime(0);
                    worldInfo.setRaining(false);
                    worldInfo.setThundering(false);
                    break;
                case Rain:
                    worldInfo.setCleanWeatherTime(0);
                    worldInfo.setRainTime(i);
                    worldInfo.setThunderTime(i);
                    worldInfo.setRaining(true);
                    worldInfo.setThundering(false);
                    break;
                case Thunder:
                    worldInfo.setCleanWeatherTime(0);
                    worldInfo.setRainTime(i);
                    worldInfo.setThunderTime(i);
                    worldInfo.setRaining(true);
                    worldInfo.setThundering(true);
                    break;
            }
        }
    }

    private enum WeatherModes {
        Clear,Rain,Thunder
    }
}
