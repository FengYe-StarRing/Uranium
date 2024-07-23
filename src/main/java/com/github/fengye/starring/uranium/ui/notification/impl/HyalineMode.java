package com.github.fengye.starring.uranium.ui.notification.impl;

import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.ui.hud.element.Horizontal;
import com.github.fengye.starring.uranium.ui.hud.element.Side;
import com.github.fengye.starring.uranium.ui.notification.Notification;

import java.awt.*;

public class HyalineMode extends Notification {
    public HyalineMode(String title, String[] texts, NotificationType type) {
        super(title, texts, type);
    }

    @Override
    public float draw(float y,Side side) {
        Horizontal horizontal = side.getHorizontal();
        FontRender font = FontManager.harmony18;
        font.drawString(getTitle(),0,y, Color.cyan.getRGB());
        return font.getHeight(getTitle());
    }
}
