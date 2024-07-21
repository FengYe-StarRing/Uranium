package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.manager.impl.ModuleManager;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Binds extends Command {
    public Binds() {
        super("Binds", new String[]{"Binds","b"}, "b <list/clear/bind> <module> <key>");
    }

    @Override
    public boolean execute(String[] args) {
        int length = args.length;
        String op = null;
        String moduleName = null;
        String key = null;
        if(length >= 1) {
            op = args[0];
        }
        if(length >= 2) {
            moduleName = args[1];
        }
        if(length >= 3) {
            key = args[2];
        }
        if(length == 1) {
            List<Module> haveKeyModule = getModulesHaveKey();
            switch (op.toLowerCase()) {
                case "list":
                    String message = "按键绑定列表:";
                    if(haveKeyModule.isEmpty()) {
                        message = message.concat(" Empty");
                    }
                    sendMessage(message);
                    for (Module module : haveKeyModule) {
                        sendMessage(module.getName() + " --> " + Keyboard.getKeyName(module.getKeyBind()));
                    }
                    break;
                case "clear":
                    for (Module module : haveKeyModule) {
                        module.setKeyBind(Keyboard.KEY_NONE);
                    }
                    sendMessage("已清除所有按键绑定");
                    break;
                default:
                    return true;
            }
            return false;
        } else if(length == 3) {
            Module module = getManager().getModuleByName(moduleName);
            if(module == null) {
                sendMessage("未找到名为'" + moduleName + "'的模块");
                return false;
            }
            module.setKeyBind(Keyboard.getKeyIndex(key));
            sendMessage("已将模块'" + moduleName + "'绑定到 " + Keyboard.getKeyName(module.getKeyBind()));
            return false;
        }
        return true;
    }

    private List<Module> getModulesHaveKey() {
        return getManager().getModulesHaveKey();
    }

    private ModuleManager getManager() {
        return Client.instance.moduleManager;
    }
}
