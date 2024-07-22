package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.file.ClientFile;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.api.file.config.impl.ElementsConfig;
import com.github.fengye.starring.uranium.api.file.config.impl.ValuesConfig;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.timer.DateUtils;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.timer.Timer;

import java.io.File;
import java.io.IOException;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.mcDataDir;

public class FileManager extends Manager implements Listenable {
    public File mainDir = null;
    public File userDataDir = null;
    public File cacheDataDir = null;
    public File logsDir = null;
    public File configDir = null;
    public Config valuesConfig = null;
    public Config elementsConfig = null;
    public ClientFile logsFile = null;

    private boolean init = false;
    private final Timer saveTimer = new Timer();
    private final int saveInterval = 15 * 1000;

    public FileManager() {
        super("FileManager");
    }

    @Override
    public void init() {
        super.init();
        init = false;
        saveTimer.reset();
        Client.instance.eventManager.registerListener(this);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle
    private void onEvent(Event event) {
        initDir();
        if(init && !Client.instance.isStop() && saveTimer.hasTimePassed(saveInterval)) {
            saveAllConfigs();
        }
    }

    private void initDir() {
        if(mcDataDir == null) {
            return;
        }
        String clientName = Client.instance.languageManager.getText(LanguageManager.Languages.English,Client.T_NAME);
        {
            if(mainDir == null) {
                mainDir = new File(mcDataDir,clientName + "-" + Client.getVersion());
            }
            if(userDataDir == null) {
                userDataDir = new File(mcDataDir,clientName + "-UserData");
            }
            if(cacheDataDir == null) {
                cacheDataDir = new File(mcDataDir,clientName + "-CacheData");
            }
            if(logsDir == null) {
                logsDir = new File(cacheDataDir, "logs");
            }
            if(configDir == null) {
                configDir = new File(mainDir, "config");
            }
            if(!mainDir.exists()) {
                mainDir.mkdirs();
            }
            if(!userDataDir.exists()) {
                userDataDir.mkdirs();
            }
            if(!cacheDataDir.exists()) {
                cacheDataDir.mkdirs();
            }
            if(!logsDir.exists()) {
                logsDir.mkdirs();
            }
            if(!configDir.exists()) {
                configDir.mkdirs();
            }
        }
        {
            if(valuesConfig == null) {
                valuesConfig = new ValuesConfig(mainDir,"Values.txt");
            }
            if(elementsConfig == null) {
                elementsConfig = new ElementsConfig(mainDir,"Elements.txt");
            }
            if(!init) {
                loadAllConfigs();
            }
            if(logsFile == null) {
                logsFile = new ClientFile(logsDir, DateUtils.getDefaultTime(DateUtils.fileFormat) + ".txt");
            }
        }
        init = true;
    }

    public void loadAllConfigs() {
        for (Config config : JavaUtils.getConfigs(this)) {
            try {
                if(!config.hasFile()) {
                    config.create();
                } else {
                    config.loadConfig();
                }
            } catch (IOException ignored) {}
        }
    }

    public void saveAllConfigs() {
        for (Config config : JavaUtils.getConfigs(this)) {
            try {
                if(!config.hasFile()) {
                    config.create();
                } else {
                    config.saveConfig();
                }
            } catch (IOException ignored) {}
        }
        saveTimer.reset();
    }
}
