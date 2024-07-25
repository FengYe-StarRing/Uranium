package com.github.fengye.starring.uranium.api.file.config.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.manager.impl.HUDManager;
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
        HUDManager hudManager = Client.instance.hudManager;
        Element element = null;
        boolean start = false;
        hudManager.getElements().clear();
        for (String line : Files.readAllLines(getFile().toPath())) {
            if(line.equals("{")) {
                start = true;
                continue;
            }
            if(line.equals("}")) {
                start = false;
                hudManager.addElement(element);
                element = null;
                continue;
            }
            if(start) {
                line = line.replaceAll("\t","");
                if(element == null) {
                    element = hudManager.newElement(line);
                    continue;
                }
                String[] data = line.split("\\|");
                String valueName = data[0];
                String valueData = data[1];
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
                    case "Lock":
                        element.setLock(Boolean.parseBoolean(valueData));
                        break;
                    default:
                        for (Value<?> value : element.getValues()) {
                            if(value.getName().equals(valueName)) {
                                value.setAuto(valueData);
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void saveConfig() throws IOException {
        String data = "";
        for (Element element : Client.instance.hudManager.getElements()) {
            String prefix = "\t";
            String postfix = "\n";
            data = data.concat("{" + postfix);
            data = data.concat(prefix + element.T_NAME + postfix);
            data = data.concat(prefix + "X|" + element.getX() + postfix);
            data = data.concat(prefix + "Y|" + element.getY() + postfix);
            data = data.concat(prefix + "Horizontal|" + element.getSide().getHorizontal() + postfix);
            data = data.concat(prefix + "Vertical|" + element.getSide().getVertical() + postfix);
            data = data.concat(prefix + "Lock|" + element.isLock() + postfix);
            for (Value<?> value : element.getValues()) {
                data = data.concat(prefix + value.getName() + "|" + value.getAsString() + postfix);
            }
            data = data.concat("}" + postfix);
        }
        Files.write(getFile().toPath(),data.getBytes());
    }
}
