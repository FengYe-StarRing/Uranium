package com.github.fengye.starring.uranium.api.value.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.ArrayValue;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;

import java.util.ArrayList;
import java.util.List;

public class FontValue extends ArrayValue<FontRender> {
    public FontValue(String name, FontRender value) {
        super(name, Client.instance.fontManager.getFonts(), value);
    }

    @Override
    public void setAuto(String value) {
        set(value);
    }

    @Override
    public void set(String value) {
        set(getManager().getFont(value));
    }

    @Override
    public String[] getModesAsStr() {
        List<String> strings = new ArrayList<>();
        for (FontRender mode : getModes()) {
            strings.add(getManager().getFontInfo(getManager().getInfoByFont(mode)));
        }
        return strings.toArray(new String[0]);
    }

    @Override
    public String getAsString() {
        return getManager().getFontInfo(getManager().getInfoByFont(get()));
    }

    private FontManager getManager() {
        return Client.instance.fontManager;
    }
}
