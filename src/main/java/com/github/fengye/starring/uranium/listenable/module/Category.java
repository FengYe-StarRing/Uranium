package com.github.fengye.starring.uranium.listenable.module;

public enum Category {
    Combat,Render,Movement,Player,World,Misc;

    private final String name;

    Category() {
        this.name = "Module.Category." + this.name();
    }

    public String getName() {
        return name;
    }
}
