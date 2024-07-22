package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.manager.impl.ConfigManager;

import java.util.List;

public class Config extends Command {
    public Config() {
        super("Config", new String[]{"Config","c"}, "c <load/save/list/clear/delete> <config>");
    }

    @Override
    public boolean execute(String[] args) {
        int length = args.length;
        String op = null;
        String config = null;
        ConfigManager manager = getManager();
        if(length >= 1) {
            op = args[0];
        }
        if(length >= 2) {
            config = args[1];
        }
        if(length == 1) {
            switch (op.toLowerCase()) {
                case "list":
                    List<String> configs = getManager().getConfigList();
                    sendMessage("配置列表: " + (configs.isEmpty() ? "Empty" : ""));
                    for (String s : configs) {
                        sendMessage(s);
                    }
                    break;
                case "clear":
                    getManager().clear();
                    sendMessage("已删除所有配置");
                    break;
                default:
                    return true;
            }
            return false;
        } else if(length == 2) {
            switch (op.toLowerCase()) {
                case "load":
                    if(manager.loadConfig(config,true)) {
                        sendMessage("加载配置成功!");
                    } else {
                        sendMessage("加载配置失败!");
                    }
                    break;
                case "save":
                    if(manager.saveConfig(config)) {
                        sendMessage("保存配置成功!");
                    } else {
                        sendMessage("保存配置失败!");
                    }
                    break;
                case "delete":
                    manager.deleteConfig(config);
                    sendMessage("已删除配置'" + config + '\'');
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
