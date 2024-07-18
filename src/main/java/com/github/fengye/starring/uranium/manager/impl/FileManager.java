package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.api.file.config.impl.ValuesConfig;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.mcDataDir;

public class FileManager extends Manager implements Listenable {
    private File configDir = null;
    private File userDataDir = null;
    private Config valuesConfig = null;
    private boolean init = false;

    public FileManager() {
        super("FileManager");
    }

    @Override
    public void init() {
        super.init();
        init = false;
        Client.instance.eventManager.registerListener(this);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle
    private void onEvent(Event event) {
        initDir();
        if(init) {
            saveAllConfigs();
        }
    }

    private void initDir() {
        if(mcDataDir == null) {
            return;
        }
        String clientName = Client.instance.languageManager.getText(LanguageManager.LangMode.English,Client.T_NAME);
        {
            if(configDir == null) {
                configDir = new File(mcDataDir,clientName + "-" + Client.getVersion());
            }
            if(userDataDir == null) {
                userDataDir = new File(mcDataDir,clientName + "-UserData");
            }
            if(!configDir.exists()) {
                configDir.mkdirs();
            }
            if(!userDataDir.exists()) {
                userDataDir.mkdirs();
            }
        }
        {
            if(valuesConfig == null) {
                valuesConfig = new ValuesConfig(configDir,"Values.txt");
            }
            if(!init) {
                loadAllConfigs();
            }
        }
        init = true;
    }

    private void loadAllConfigs() {
        for (Field field : JavaUtils.getFields(this, Config.class)) {
            try {
                Config config = ((Config) field.get(this));
                if(!config.hasFile()) {
                    config.createFile();
                } else {
                    config.loadConfig();
                }
            } catch (IOException | IllegalAccessException ignored) {}
        }
    }

    public void saveAllConfigs() {
        for (Field field : JavaUtils.getFields(this, Config.class)) {
            try {
                Config config = ((Config) field.get(this));
                if(!config.hasFile()) {
                    config.createFile();
                }
                config.saveConfig();
            } catch (IOException | IllegalAccessException ignored) {}
        }
    }
}
