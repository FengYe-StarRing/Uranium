package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.ui.hud.element.Border;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Side;

import java.awt.*;

public class Logo extends Element {
    public Logo() {
        super("Logo",3,3, Side.toDefault());
    }

    @Override
    public Border render() {
        FontRender font = FontManager.harmony48;
        font.drawString(Client.getName(),0,0, Color.cyan.getRGB());
        return new Border(0,0,font.getStringWidth(Client.getName()),font.getHeight(Client.getName()));
    }
}
