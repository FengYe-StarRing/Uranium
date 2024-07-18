package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.font.FastUniFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class FontManager extends Manager {
    public static FastUniFontRenderer harmony18;
    public static FastUniFontRenderer harmony24;

    public FontManager() {
        super("FontManager");
        // Harmony
        String harmony = "HarmonyOS_Sans_SC.ttf";
        harmony18 = getFastUniFont(18,harmony);
        harmony24 = getFastUniFont(24,harmony);
    }

    @Override
    public void init() {
        super.init();
    }

    public static FastUniFontRenderer getFastUniFont(int size, String name) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Client.RESOURCES + "/Fonts/" + name)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            font = new Font("default", Font.PLAIN, size);
        }
        return new FastUniFontRenderer(font, size, true);
    }

    public static Font getFonts(int size, String name) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Client.RESOURCES + "/Fonts/" + name)).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            font = new Font("default", Font.PLAIN, size);
        }
        return font;
    }
}
