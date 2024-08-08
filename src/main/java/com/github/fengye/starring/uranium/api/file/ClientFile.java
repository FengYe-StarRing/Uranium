package com.github.fengye.starring.uranium.api.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ClientFile {
    private final File dir;
    private final String path;
    private final File file;

    public ClientFile(File dir, String path) {
        this.dir = dir;
        this.path = path;
        file = new File(dir,path);
    }

    public void create() {
        try {
            if(!hasFile()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void writeLine(String content,OpenOption... options) {
        write(content + '\n',options);
    }

    public void write(String content, OpenOption... options) {
        try {
            Files.write(getFile().toPath(),content.getBytes(), options);
        } catch (IOException ignored) {}
    }

    public void write(String content) {
        write(content,StandardOpenOption.APPEND,StandardOpenOption.CREATE);
    }

    public void writeLine(String content) {
        writeLine(content,StandardOpenOption.APPEND,StandardOpenOption.CREATE);
    }

    public void delete() {
        file.delete();
    }

    public List<String> readAllLines() {
        if(!hasFile()) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(getFile().toPath());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
