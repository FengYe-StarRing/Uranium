package com.github.fengye.starring.uranium.ui.buttons;

import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.mouse.MouseUtils;
import com.github.fengye.starring.uranium.utils.render.AnimationUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class BorderlessButton extends GuiButton {
    private final FontRender font = getFont();

    private final int interval = 1;

    private AnimModes animMode;
    private int anim1 = 0;

    public BorderlessButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        setWidth(font.getStringWidth(buttonText));
        setHeight(font.getStringHeight(buttonText) + interval * 2);
        animMode = AnimModes.BottomLine;
    }

    public BorderlessButton(int buttonId, int x, int y, String buttonText,AnimModes mode) {
        this(buttonId,x,y,buttonText);
        animMode = mode;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            int x1 = xPosition;
            int y1 = yPosition;
            int width = this.width;
            int height = this.height;
            int x2 = xPosition + width;
            int y2 = yPosition + height;
            this.hovered = MouseUtils.isHovered(x1,y1,x2,y2);
            font.drawCenteredString(displayString,x1 + width / 2F,y1 + height / 2F - font.getStringHeight(displayString) / 2F, Color.white.getRGB());
            {
                int speed = width * 4;
                if(isMouseOver()) {
                    anim1 = AnimationUtils.move(anim1,width / 2, speed);
                } else {
                    anim1 = AnimationUtils.move(anim1,0, speed);
                }
            }
            switch (animMode) {
                case BottomLine: {
                    int halfX = x1 + width / 2;
                    int top = y2 - interval;
                    RenderUtils.drawRect(halfX,top,halfX - anim1,y2,Color.white);
                    RenderUtils.drawRect(halfX,top,halfX + anim1,y2,Color.white);
                    break;
                }
                case TopLine: {
                    int halfX = x1 + width / 2;
                    int bottom = y1 + interval;
                    RenderUtils.drawRect(halfX,y1,halfX - anim1,bottom,Color.white);
                    RenderUtils.drawRect(halfX,y1,halfX + anim1,bottom,Color.white);
                    break;
                }
            }
        }
    }

    public void setAnimMode(AnimModes animMode) {
        this.animMode = animMode;
    }

    public enum AnimModes {
        BottomLine,TopLine
    }
}
