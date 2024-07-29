package com.github.fengye.starring.uranium.listenable.module;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import net.minecraft.util.ResourceLocation;

public enum Category {
    Combat,Render,Movement,Player,World,Misc;

    private final String name;
    private final ResourceLocation icon;

    Category() {
        this.name = Client.instance.languageManager.getTranslate("Module.Category." + this.name());
        this.icon = MinecraftInstance.getResourceLocation("/CategoryIcons/" + this.name() + ".png");
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getIcon() {
        return icon;
    }
}
