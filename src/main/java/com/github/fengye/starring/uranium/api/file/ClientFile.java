package com.github.fengye.starring.uranium.api.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

    public void writeLine(String content) {
        write(content + '\n');
    }

    public void write(String content) {
        try {
            if(!hasFile()) {
                createFile();
            }
            Files.write(getFile().toPath(),content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
