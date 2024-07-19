package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FastUniFontRenderer;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Side;

import java.awt.*;

public class Logo extends Element {
    public Logo() {
        super("Logo",3,3, Side.toDefault());
    }

    @Override
    public void render() {
        FastUniFontRenderer font = FontManager.harmony36;
        font.drawString(Client.getName(),0,0, Color.cyan.getRGB());
    }
}
