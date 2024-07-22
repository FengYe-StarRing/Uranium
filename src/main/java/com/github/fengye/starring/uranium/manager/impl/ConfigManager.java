package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.file.ClientFile;
import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.timer.DateUtils;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.misc.ZipWordUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager extends Manager {
    private final String POSTFIX = ".zip";

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
        fileManager.saveAllConfigs();
        for (Config config : JavaUtils.getConfigs(fileManager)) {
            files.add(config.getFile());
        }
        ClientFile info = new ClientFile(fileManager.configDir,"info.txt");
        info.create();
        info.writeLine("Name|" + "ConfigName");
        info.writeLine("Author|" + "StarRing");
        info.writeLine("Date|" + DateUtils.getTime(DateUtils.dataFormat));
        info.writeLine("Version|" + Client.getVersion());
        info.writeLine("Description|" + "StarRing Config");
        files.add(info.getFile());
        boolean ret =  ZipWordUtils.createZipWord(fileManager.configDir.getAbsolutePath() + "/" + name + POSTFIX, files.toArray(new File[0]));
        info.delete();
        return ret;
    }

    public FileManager getManager() {
        return Client.instance.fileManager;
    }

    public boolean loadConfig(String config,boolean noti) {
        FileManager fileManager = getManager();
        boolean ret = ZipWordUtils.unzipFile(fileManager.configDir.getAbsolutePath() + "/" +config + POSTFIX, fileManager.mainDir.getAbsolutePath());
        ClientFile info = new ClientFile(fileManager.mainDir,"info.txt");
        fileManager.loadAllConfigs();
        if(noti && ret) {
            String name = null;
            String author = null;
            String date = null;
            String version = null;
            String description = null;
            for (String line : info.readAllLines()) {
                String[] datas = line.split("\\|");
                if(datas.length != 2) {
                    continue;
                }
                String data = datas[1];
                switch (datas[0]) {
                    case "Name":
                        name = data;
                        break;
                    case "Author":
                        author = data;
                        break;
                    case "Date":
                        date = DateUtils.changeFormat(data,DateUtils.dataFormat,DateUtils.renderFormat);
                        break;
                    case "Version":
                        version = data;
                        break;
                    case "Description":
                        description = data;
                        break;
                }
            }
            String text;
            TrayIcon.MessageType type;
            if(name == null || author == null || date == null || version == null || description == null) {
                type = TrayIcon.MessageType.ERROR;
                text = "配置[" + ((name == null) ? config : name) + "]信息读取失败";
            } else {
                type = TrayIcon.MessageType.INFO;
                text = "配置[" + name + "]加载成功";
                if(version.equals(Client.getVersion())) {
                    text = text.concat("!\n");
                } else {
                    type = TrayIcon.MessageType.WARNING;
                    text = text.concat(",但版本不匹配\n");
                }
                text = text.concat("作者: " + author + "\n日期: " + date + "\n描述: " + description);
            }
            Client.displayTray(text,type);
        }
        info.delete();
        return ret;
    }

    public void deleteConfig(String name) {
        new ClientFile(getManager().configDir,name + POSTFIX).delete();
    }

    public List<String> getConfigList() {
        FileManager fileManager = getManager();
        List<String> configs = new ArrayList<>();
        File dir = fileManager.configDir;
        if(dir == null) {
            return configs;
        }
        File[] files = dir.listFiles();
        if(files == null) {
            return configs;
        }
        for (File file : files) {
            String fileName = file.getName();
            String[] splits = fileName.split("\\.");
            if(('.' + splits[splits.length - 1]).equals(POSTFIX)) {
                configs.add(fileName);
            }
        }
        return configs;
    }

    public void clear() {
        FileManager fileManager = getManager();
        for (String s : getConfigList()) {
            new ClientFile(fileManager.configDir,s).delete();
        }
    }
}
