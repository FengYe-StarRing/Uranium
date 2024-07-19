package com.github.fengye.starring.uranium.api.file.config.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.api.value.Numbers;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Horizontal;
import com.github.fengye.starring.uranium.ui.hud.element.Vertical;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ElementsConfig extends Config {
    public ElementsConfig(File dir, String path) {
        super(dir, path);
    }

    @Override
    public void loadConfig() throws IOException {
        for (String line : Files.readAllLines(getFile().toPath())) {
            String[] data = line.split("\\|");
            if(data.length != 3) {
                continue;
            }
            String elementName = data[0];
            String valueName = data[1];
            String valueData = data[2];
            for (Element element : Client.instance.hudManager.getElements()) {
                if(element.T_NAME.equals(elementName)) {
                    switch (valueName) {
                        case "X":
                            element.setX(Float.parseFloat(valueData));
                            break;
                        case "Y":
                            element.setY(Float.parseFloat(valueData));
                            break;
                        case "Horizontal":
                            element.getSide().setHorizontal(Horizontal.valueOf(valueData));
                            break;
                        case "Vertical":
                            element.getSide().setVertical(Vertical.valueOf(valueData));
                            break;
                        default:
                            for (Value value : element.getValues()) {
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
                            }
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void saveConfig() throws IOException {
        String data = "";
        for (Element element : Client.instance.hudManager.getElements()) {
            String prefix = element.T_NAME + "|";
            String postfix = "\n";
            data = data.concat("# " + element.T_NAME + postfix);
            data = data.concat(prefix + "X|" + element.getX() + postfix);
            data = data.concat(prefix + "Y|" + element.getY() + postfix);
            data = data.concat(prefix + "Horizontal|" + element.getSide().getHorizontal() + postfix);
            data = data.concat(prefix + "Vertical|" + element.getSide().getVertical() + postfix);
            for (Value<?> value : element.getValues()) {
                data = data.concat(prefix + value.getName() + "|" + value.get() + postfix);
            }
        }
        Files.write(getFile().toPath(),data.getBytes());
    }
}
