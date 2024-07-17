package com.github.fengye.starring.uranium.listenable.module;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.manager.impl.LanguageManager;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

public abstract class Module extends MinecraftInstance implements Listenable {
    public final String NAME;
    private final String name;
    private final String description;
    private final Category category;
    private final boolean canEnable;

    private boolean state = false;
    private int keyBind;

    public Module() {
        ModuleInfo info = JavaUtils.getClassLoader(this).getAnnotation(ModuleInfo.class);
        LanguageManager lang = Client.instance.languageManager;

        NAME = info.name();

        category = info.category();
        name = lang.getText("L.Module." + NAME);
        description = lang.getText("L.Module." + NAME + ".Description");
        keyBind = info.keyBind();
        canEnable = info.canEnable();
    }

    @Override
    public boolean handleEvents() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isCanEnable() {
        return canEnable;
    }

    public void setEnabled(boolean state) {
        this.state = state;
    }

    public void setEnabled() {
        setEnabled(!state);
    }

    public boolean isEnable() {
        return state;
    }
}
