package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager extends Manager {
    private final List<String> packs = new ArrayList<>();
    private final List<String> texts = new ArrayList<>();

    public LanguageManager() {
        super("LanguageManager");
    }

    @Override
    public void init() {
        super.init();
        packs.clear();
        texts.clear();
        InputStream stream = getLangStream();
        String data = "";
        try {
            int ch = stream.read();
            while (ch != -1) {
                data = data.concat(String.valueOf((char) ch));
                ch = stream.read();
            }
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String string : data.split("\n")) {
            int index = 0;
            String[] strings = string.split("=");
            if(strings.length <= 1) {
                continue;
            }
            for (String s : strings) {
                switch (index) {
                    case 0:
                        packs.add(s);
                        break;
                    case 1:
                        texts.add(s);
                        break;
                }
                index++;
            }
        }
    }

    private String getLangPath() {
        return LangMode.English.name() + ".lang";
    }

    private InputStream getLangStream() {
        return JavaUtils.getStream(Client.RESOURCES + "/Lang/" + getLangPath());
    }

    public String getText(String pack) {
        int index = 0;
        String text = null;
        while (index < packs.size()) {
            if(packs.get(index).equals(pack)) {
                text = texts.get(index);
            }
            index++;
        }
        return text;
    }

    public enum LangMode {
        English,Chinese,Japanese,TChinese
    }
}
