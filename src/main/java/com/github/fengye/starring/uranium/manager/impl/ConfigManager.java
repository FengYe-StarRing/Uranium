package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.misc.ZipWordUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager extends Manager {
    public ConfigManager() {
        super("ConfigManager");
    }

    @Override
    public void init() {
        super.init();
    }

    public boolean saveConfig(String name) {
        FileManager fileManager = getManager();
        List<File> files = new ArrayList<>();
        for (Config config : JavaUtils.getConfigs(fileManager)) {
            files.add(config.getFile());
        }
        return ZipWordUtils.createZipWord(fileManager.configDir.getAbsolutePath() + "/" + name + ".zip", files.toArray(new File[0]));
    }

    public FileManager getManager() {
        return Client.instance.fileManager;
    }
}
