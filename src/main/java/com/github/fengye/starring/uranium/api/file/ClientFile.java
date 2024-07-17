package com.github.fengye.starring.uranium.api.file;

import java.io.File;
import java.io.IOException;

public class ClientFile {
    private final File dir;
    private final String path;
    private final File file;

    public ClientFile(File dir, String path) {
        this.dir = dir;
        this.path = path;
        file = new File(dir,path);
    }

    public void createFile() throws IOException {
        if(!hasFile()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
    }

    public boolean hasFile() {
        return file.exists();
    }

    public File getFile() {
        return file;
    }

    public File getDir() {
        return dir;
    }

    public String getPath() {
        return path;
    }
}
