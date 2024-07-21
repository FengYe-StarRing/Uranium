package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.manager.impl.ConfigManager;

public class Config extends Command {
    public Config() {
        super("Config", new String[]{"Config","c"}, "c <load/save/list/clear> <config>");
    }

    @Override
    public boolean execute(String[] args) {
        int length = args.length;
        String op = null;
        String config = null;
        if(length >= 1) {
            op = args[0];
        }
        if(length >= 2) {
            config = args[1];
        }
        if(length == 1) {
            switch (op.toLowerCase()) {
                case "list":
                    break;
                case "clear":
                    break;
                default:
                    return true;
            }
            return false;
        } else if(length == 2) {
            switch (op.toLowerCase()) {
                case "load":
                    break;
                case "save":
                    if(getManager().saveConfig(config)) {
                        sendMessage("保存配置成功!");
                    } else {
                        sendMessage("保存配置失败!");
                    }
                    break;
                default:
                    return true;
            }
            return false;
        }
        return true;
    }

    private ConfigManager getManager() {
        return Client.instance.configManager;
    }
}
