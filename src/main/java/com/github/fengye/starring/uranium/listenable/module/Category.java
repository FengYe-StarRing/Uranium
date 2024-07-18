package com.github.fengye.starring.uranium.listenable.module;

import com.github.fengye.starring.uranium.Client;

public enum Category {
    Combat,Render,Movement,Player,World,Misc;

    private final String name;

    Category() {
        this.name = Client.instance.languageManager.getText("Module.Category." + this.name());
    }

    public String getName() {
        return name;
    }
}
