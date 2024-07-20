package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.command.Command;

public class Help extends Command {
    public Help() {
        super(null, new String[]{"help","h"}, null);
    }

    @Override
    public boolean execute(String[] args) {
        sendMessage("帮助列表:");
        for (Command command : Client.instance.commandManager.getCommands()) {
            String name = command.getName();
            String help = command.getHelp();
            if(name != null && help != null) {
                sendMessage(name + ": " + help);
            }
        }
        return false;
    }
}
