package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.manager.impl.NotificationManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.ui.hud.element.*;
import com.github.fengye.starring.uranium.utils.render.AnimationUtils;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Notifications extends Element {
    private final FontRender drawFont = FontManager.alibaba18;
    private final FontRender drawTitleFont = FontManager.robotoBold18;
    private final int drawIntervalY = 1;

    private final int imageSideLength = 18;

    private final int intervalX = 3;
    private final int intervalY = 2;

    public Notifications() {
        super("Notifications", 3, 3, new Side(Horizontal.Right, Vertical.Down));
        setLock(true);
    }

    @Override
    public Border render() {
        lock(3,3);
        drawNotifs();
        {
            NotificationManager.Notif notif = new NotificationManager.Notif("Notifications",new String[]{}, NotificationManager.NotifType.Info);
            int width = getBorderWidth(notif),height = getBorderHeight(notif);
            return new Border(-(width / 2),-(height / 2),width,height);
        }
    }

    private int getBorderWidth(NotificationManager.Notif notif) {
        int drawWidth = drawTitleFont.getStringWidth(notif.getTitle());
        NotificationManager.NotifType type = notif.getType();
        ResourceLocation icon = type.getIcon();
        for (String text : notif.getTexts()) {
            int w = drawFont.getStringWidth(text);
            if(w > drawWidth) {
                drawWidth = w;
            }
        }
        return drawWidth + intervalX * 2 + (icon != null ? intervalX + imageSideLength : 0);
    }

    private int getBorderHeight(NotificationManager.Notif notif) {
        int drawHeight = drawTitleFont.getStringHeight(notif.getTitle()) + drawIntervalY;
        String[] texts = notif.getTexts();
        for (String text : texts) {
            drawHeight += drawFont.getStringHeight(text) + drawIntervalY;
        }
        if(texts.length != 0) {
            drawHeight -= drawIntervalY;
        }
        return drawHeight + intervalY * 2;
    }

    private List<NotificationManager.Notif> getNotifs() {
        return NotificationManager.getNotifs();
    }

    public void updateNotifs() {
        int targetY = 0;
        int interval = 4;
        Side side = getSide();
        Horizontal horizontal = side.getHorizontal();
        Vertical vertical = side.getVertical();
        int index = 0;
        List<Integer> removes = new ArrayList<>();
        List<NotificationManager.Notif> notifs = getNotifs();
        for (NotificationManager.Notif notif : notifs) {
            int x = notif.getX();
            int y = notif.getY();
            int borderWidth = getBorderWidth(notif);
            int borderHeight = getBorderHeight(notif);
            boolean hasTime = notif.hasTime();
            int targetX = hasTime ? (horizontal.equals(Horizontal.Left) ? -borderWidth : borderWidth) : 0;
            if(x != targetX) {
                x = AnimationUtils.move(x,targetX,borderWidth * 2);
            }
            if(y != targetY) {
                y = AnimationUtils.move(y,targetY,borderHeight * notifs.size() * 2);
            }
            if(x == targetX) {
                if(y == targetY && !notif.countdownEnable) {
                    notif.enableTimer();
                }
                if(hasTime) {
                    notif.toBeDeleted = true;
                    removes.add(index);
                }
            }
            notif.setX(x);
            notif.setY(y);
            targetY += (borderHeight + interval) * (vertical == Vertical.Up ? 1 : -1);
            index++;
        }
        for (Integer remove : removes) {
            int value = remove;
            if(value >= notifs.size() || !notifs.get(value).toBeDeleted) {
                continue;
            }
            notifs.remove(value);
        }
    }

    private void drawNotifs() {
        Side side = getSide();
        Horizontal horizontal = side.getHorizontal();
        Vertical vertical = side.getVertical();
        for (NotificationManager.Notif notif : new ArrayList<>(getNotifs())) {
            int x = notif.getX();
            int y = notif.getY();
            int borderWidth = getBorderWidth(notif);
            int borderHeight = getBorderHeight(notif);
            NotificationManager.NotifType type = notif.getType();
            ResourceLocation icon = type.getIcon();
            String title = notif.getTitle();
            String[] texts = notif.getTexts();
            if(horizontal == Horizontal.Right) {
                x -= borderWidth;
            }
            if(vertical == Vertical.Down) {
                y -= borderHeight;
            }
            int x2 = x + borderWidth;
            int y2 = y + borderHeight;
            Color transparent1 = ColorUtils.transparent();
            RenderUtils.drawShadow(x,y,borderWidth,borderHeight,transparent1);
            RenderUtils.drawRoundRect(x,y,x2,y2,4,transparent1);
            {
                // 画X轴的内容
                int startX = x + intervalX;
                if(icon != null) {
                    RenderUtils.drawImage(notif.getType().getIcon(),startX,y + borderHeight / 2 - imageSideLength / 2,imageSideLength,imageSideLength);
                    startX += imageSideLength + intervalX;
                }
                // 画Y轴的内容
                int color = Color.gray.getRGB();
                int startY = y + intervalY;
                drawTitleFont.drawString(title,startX, startY,Color.white.getRGB());
                startY += drawTitleFont.getStringHeight(title) + drawIntervalY;
                for (String text : texts) {
                    drawFont.drawString(text,startX,startY,color);
                    startY += drawFont.getStringHeight(text) + drawIntervalY;
                }
            }
        }
    }
}
