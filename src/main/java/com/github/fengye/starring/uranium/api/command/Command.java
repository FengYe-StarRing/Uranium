package com.github.fengye.starring.uranium.api.command;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;

public abstract class Command extends MinecraftInstance {
    private final String[] alias;
    private final String syntax;
    private final String help;

    public Command(String name,String[] alias,String syntax) {
        this.alias = alias;
        this.syntax = syntax;
        help = Client.instance.languageManager.getText(name);
    }

    public String[] getAlias() {
        return alias;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getHelp() {
        return help;
    }

    public boolean execute(String[] args) {
        return false;
    }
}
