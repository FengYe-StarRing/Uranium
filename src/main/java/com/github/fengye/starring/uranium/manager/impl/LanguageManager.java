package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager extends Manager {
    private final ModeValue languageValue = new ModeValue("Language", Languages.values(), Languages.English);

    public LanguageManager() {
        super("LanguageManager");
    }

    @Override
    public void init() {
        super.init();
        load(getLang());
    }

    private String getLangPath(Languages mode) {
        return mode.name() + ".lang";
    }

    private InputStream getLangStream(Languages mode) {
        return JavaUtils.getStream(Client.RESOURCES + "/Lang/" + getLangPath(mode));
    }

    public String getTranslate(Languages mode, String pack) {
        List<String> packs = mode.getPacks();
        List<String> texts = mode.getTexts();
        if(packs == null || texts == null) {
            load(mode);
            packs = mode.getPacks();
            texts = mode.getTexts();
        }
        if(packs == null || texts == null) {
            return null;
        }
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

    public String getTranslate(String pack) {
        return getTranslate(Languages.valueOf(languageValue.getAsString()),pack);
    }

    public void load(Languages mode) {
        List<String> packs = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        InputStream buffer = getLangStream(mode);
        Reader stream = new InputStreamReader(buffer,StandardCharsets.UTF_8);
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
            if(strings.length != 2) {
                continue;
            }
            for (String s : strings) {
                switch (index) {
                    case 0:
                        packs.add(s);
                        break;
                    case 1:
                        texts.add(s.replaceAll(String.valueOf((char) 13),""));
                        break;
                }
                index++;
            }
        }
        mode.setPacks(packs);
        mode.setTexts(texts);
    }

    public Languages getLang() {
        return Languages.valueOf(languageValue.getAsString());
    }

    public enum Languages {
        English,Chinese,Japanese,TChinese;

        private List<String> packs = null;
        private List<String> texts = null;

        public List<String> getPacks() {
            return packs;
        }

        public List<String> getTexts() {
            return texts;
        }

        public void setPacks(List<String> packs) {
            this.packs = packs;
        }

        public void setTexts(List<String> texts) {
            this.texts = texts;
        }
    }
}
