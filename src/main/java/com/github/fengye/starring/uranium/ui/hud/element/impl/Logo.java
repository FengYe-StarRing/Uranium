package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.special.Palette;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.ui.hud.element.Border;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Side;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;

public class Logo extends Element {
    public Logo() {
        super("Logo",3,3, Side.toDefault());
    }

    @Override
    public Border render() {
        FontRender font = FontManager.robotoBold36;
        int intervalX = 4;
        int intervalY = 2;
        int startX = intervalX;
        int startY= intervalY;
        int sideLength = 28;
        String clientName = Client.getName();
        int borderWidth = sideLength + intervalX * 3 + font.getStringWidth(clientName);
        int borderHeight = 28 + intervalY * 3;
        Palette.RainbowSpeeds rainbowMode = Palette.RainbowSpeeds.Normal;
        RenderUtils.drawRound(0,0,borderWidth,borderHeight,8,ColorUtils.transparent());
        RenderUtils.drawRound(0,0,borderWidth,intervalY,2,Palette.getColor1());
        RenderUtils.drawImage("/Icons/Icon_128x128.png",startX,startY + intervalY,sideLength,sideLength);
        startX += sideLength + intervalX;
        {
            int x = startX;
            float hue = Palette.getHue(rainbowMode);
            for (char c : clientName.toCharArray()) {
                float y = startY + sideLength / 2F - font.getStringHeight(clientName) / 2F + intervalY;
                font.drawChar(c,x,y, ColorUtils.getRainbowColor(hue).getRGB());
                x += font.getCharWidth(c);
                hue = ColorUtils.addHue(hue,Palette.getRainbowSpeed(rainbowMode));
            }
        }
        return new Border(0,0,borderWidth,borderHeight);
    }
}
