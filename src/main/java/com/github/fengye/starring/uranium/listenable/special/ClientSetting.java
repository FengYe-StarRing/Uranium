package com.github.fengye.starring.uranium.listenable.special;

import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import net.minecraft.util.ResourceLocation;

public class ClientSetting extends MinecraftInstance implements Listenable {
    @Override
    public boolean handleEvents() {
        return true;
    }

    public static ResourceLocation getBackground() {
        return getResourceLocation("/Backgrounds/1.png");
    }
}
