package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.listenable.module.Module;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", new String[]{"Toggle","Enable","t"}, "t <module name>");
    }

    @Override
    public boolean execute(String[] args) {
        if(args.length == 0) {
            return false;
        }
        String arg = args[0];
        boolean error = true;
        for (Module module : Client.instance.moduleManager.getModules()) {
            if(arg.equals(module.T_NAME) || arg.equals(module.getName())) {
                module.setEnabled();
                if(module.isEnabled()) {
                    sendMessage(arg + " was enabled");
                } else {
                    sendMessage(arg + " was disabled");
                }
                error = false;
            }
        }
        return error;
    }
}
