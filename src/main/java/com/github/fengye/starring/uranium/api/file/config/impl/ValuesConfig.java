package com.github.fengye.starring.uranium.api.file.config.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.api.value.Numbers;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

// ModuleName|ValueName|Value

public class ValuesConfig extends Config {
    public ValuesConfig(File dir, String path) {
        super(dir, path);
    }

    @Override
    public void loadConfig() throws IOException {
        for (String line : Files.readAllLines(getFile().toPath())) {
            String[] data = line.split("\\|");
            if(data.length != 3) {
                continue;
            }
            String moduleName = data[0];
            String valueName = data[1];
            String valueData = data[2];
            for (Module module : Client.instance.moduleManager.getModules()) {
                if(module.T_NAME.equals(moduleName)) {
                    for (Value value : module.getValues()) {
                        switch (valueName) {
                            case "Enabled":
                                module.setEnabled(Boolean.valueOf(valueData));
                                break;
                            case "KeyBind":
                                module.setKeyBind(Integer.valueOf(valueData));
                                break;
                            default:
                                if(value.getName().equals(valueName)) {
                                    if(value instanceof Numbers) {
                                        value.set(Double.valueOf(valueData));
                                    }
                                    if(value instanceof ModeValue) {
                                        value.set(valueData);
                                    }
                                    if(value instanceof OptionValue) {
                                        value.set(Boolean.valueOf(valueData));
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveConfig() throws IOException {
        String data = "";
        for (Module module : Client.instance.moduleManager.getModules()) {
            String prefix = module.T_NAME + "|";
            String postfix = "\n";
            data = "# " + module.T_NAME + postfix;
            data = data.concat(prefix + "Enabled|" + module.isEnable()) + postfix;
            data = data.concat(prefix + "KeyBind|" + module.getKeyBind()) + postfix;
            for (Value<?> value : module.getValues()) {
                data = data.concat(prefix + value.T_NAME + "|" + value.get() + postfix);
            }
        }
        Files.write(getFile().toPath(),data.getBytes());
    }
}
