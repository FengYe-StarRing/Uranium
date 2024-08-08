package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.font.FontInfo;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontManager extends Manager {
    @FontInfo(name = "McFont", size = 17)
    public static FontRender mcFont;

    @FontInfo(name = "Harmony", size = 18)
    public static FontRender harmony18;
    @FontInfo(name = "Harmony", size = 24)
    public static FontRender harmony24;
    @FontInfo(name = "Harmony", size = 36)
    public static FontRender harmony36;
    @FontInfo(name = "Harmony", size = 48)
    public static FontRender harmony48;

    @FontInfo(name = "RobotoBold",size = 12)
    public static FontRender robotoBold12;
    @FontInfo(name = "RobotoBold",size = 16)
    public static FontRender robotoBold16;
    @FontInfo(name = "RobotoBold",size = 18)
    public static FontRender robotoBold18;
    @FontInfo(name = "RobotoBold",size = 24)
    public static FontRender robotoBold24;
    @FontInfo(name = "RobotoBold",size = 30)
    public static FontRender robotoBold30;
    @FontInfo(name = "RobotoBold",size = 36)
    public static FontRender robotoBold36;
    @FontInfo(name = "RobotoBold",size = 48)
    public static FontRender robotoBold48;

    @FontInfo(name = "Alibaba",size = 12)
    public static FontRender alibaba12;
    @FontInfo(name = "Alibaba",size = 16)
    public static FontRender alibaba16;
    @FontInfo(name = "Alibaba",size = 16)
    public static FontRender alibaba17;
    @FontInfo(name = "Alibaba",size = 18)
    public static FontRender alibaba18;
    @FontInfo(name = "Alibaba",size = 20)
    public static FontRender alibaba20;
    @FontInfo(name = "Alibaba",size = 24)
    public static FontRender alibaba24;
    @FontInfo(name = "Alibaba",size = 28)
    public static FontRender alibaba28;
    @FontInfo(name = "Alibaba",size = 36)
    public static FontRender alibaba36;
    @FontInfo(name = "Alibaba",size = 48)
    public static FontRender alibaba48;

    @FontInfo(name = "Baloo",size = 18)
    public static FontRender baloo18;
    @FontInfo(name = "Baloo",size = 24)
    public static FontRender baloo24;

    public FontManager() {
        super("FontManager");
        mcFont = Minecraft.getMinecraft().fontRendererObj;
        // Harmony
        String harmony = "Harmony.ttf";
        harmony18 = loadFont(harmony,18);
        harmony24 = loadFont(harmony,24);
        harmony36 = loadFont(harmony,36);
        harmony48 = loadFont(harmony,48);
        // Alibaba
        String alibaba = "Alibaba.ttf";
        alibaba12 = loadFont(alibaba,12);
        alibaba16 = loadFont(alibaba,16);
        alibaba17 = loadFont(alibaba,17);
        alibaba18 = loadFont(alibaba,18);
        alibaba20 = loadFont(alibaba,20);
        alibaba24 = loadFont(alibaba,24);
        alibaba28 = loadFont(alibaba,28);
        alibaba36 = loadFont(alibaba,36);
        alibaba48 = loadFont(alibaba,48);
        // Facon
        String facon = "Facon.ttf";
        // RobotoBold
        String robotoBold = "RobotoBold.ttf";
        robotoBold12 = loadFont(robotoBold,12);
        robotoBold16 = loadFont(robotoBold,16);
        robotoBold18 = loadFont(robotoBold,18);
        robotoBold24 = loadFont(robotoBold,24);
        robotoBold30 = loadFont(robotoBold,30);
        robotoBold36 = loadFont(robotoBold,36);
        robotoBold48 = loadFont(robotoBold,48);
        // Baloo
        String baloo = "Baloo.ttf";
        baloo18 = loadFont(baloo,18);
        baloo24 = loadFont(baloo,24);
    }

    @Override
    public void init() {
        super.init();
    }

    public static FontRender loadFont(String location, float size) {
        Font font;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        size = size * ((float) sr.getScaleFactor() / 2);
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(getFontLocation(location)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception e) {
            font = new Font("Default", Font.PLAIN, +10);
        }
        return new FontRender(font);
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

    public static ResourceLocation getFontLocation(String name) {
        return new ResourceLocation(Client.RESOURCES + "/Fonts/" + name);
    }

    public static FontRenderer getMcFont() {
        return (FontRenderer) mcFont;
    }
}
