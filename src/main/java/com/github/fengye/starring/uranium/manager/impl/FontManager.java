package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.font.FontInfo;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontManager extends Manager {
    public static FontRenderer mcFont;

    @FontInfo(name = "Harmony", size = 17)
    public static FontRender harmony17;
    @FontInfo(name = "Harmony", size = 18)
    public static FontRender harmony18;
    @FontInfo(name = "Harmony", size = 24)
    public static FontRender harmony24;
    @FontInfo(name = "Harmony", size = 36)
    public static FontRender harmony36;
    @FontInfo(name = "Harmony", size = 48)
    public static FontRender harmony48;

    public FontManager() {
        super("FontManager");
        mcFont = Minecraft.getMinecraft().fontRendererObj;
        // Harmony
        String harmony = "Harmony.ttf";
        harmony17 = loadFont(17,harmony);
        harmony18 = loadFont(18,harmony);
        harmony24 = loadFont(24,harmony);
        harmony36 = loadFont(36,harmony);
        harmony48 = loadFont(48,harmony);
        // Alibaba
        String alibaba = "Alibaba.ttf";
        // Facon
        String facon = "Facon.ttf";
        // RobotoBold
        String robotoBold = "RobotoBold.ttf";
    }

    @Override
    public void init() {
        super.init();
    }

    public static FontRender loadFont(int size, String name) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Client.RESOURCES + "/Fonts/" + name)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            font = new Font("default", Font.PLAIN, size);
        }
        return new FontRender(font, size, true);
    }

    public FontRender getFont(String name,int size) {
        for (Field field : JavaUtils.getFields(this,FontRender.class)) {
            FontInfo info = field.getAnnotation(FontInfo.class);
            if(info != null && info.name().equals(name) && info.size() == size) {
                try {
                    return (FontRender) field.get(this);
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public FontRender[] getFonts() {
        return JavaUtils.getFonts(this).toArray(new FontRender[0]);
    }

    public FontInfo getInfoByFont(FontRender font) {
        for (Field field : JavaUtils.getFields(this,FontRender.class)) {
            try {
                if(field.get(this) == font) {
                    return field.getAnnotation(FontInfo.class);
                }
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }

    public FontRender getFont(String info) {
        Pattern pattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(info);
        if (matcher.matches()) {
            return getFont(matcher.group(1),Integer.parseInt(matcher.group(2)));
        }
        return null;
    }

    public String getFontInfo(FontInfo info) {
        return info.name() + info.size();
    }
}
