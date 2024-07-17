package com.github.fengye.starring.uranium.api.file.config;

import com.github.fengye.starring.uranium.api.file.ClientFile;

import java.io.File;
import java.io.IOException;

public abstract class Config extends ClientFile {
    public Config(File dir, String path) {
        super(dir, path);
    }

    public abstract void loadConfig() throws IOException;

    public abstract void saveConfig() throws IOException;
}
