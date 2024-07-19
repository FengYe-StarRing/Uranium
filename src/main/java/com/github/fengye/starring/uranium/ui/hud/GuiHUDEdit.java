package com.github.fengye.starring.uranium.ui.hud;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.impl.HUDManager;
import com.github.fengye.starring.uranium.ui.hud.element.Border;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.utils.misc.MouseUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.List;

public class GuiHUDEdit extends GuiScreen {
    private float lastX = getDefault(), lastY = getDefault(), moveX = 0, moveY = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        borders(mouseX,mouseY);
    }

    private void borders(int mouseX, int mouseY) {
        List<Element> elements = getElements();
        for (Element element : elements) {
            Border border = element.getBorder();
            if(border == null) {
                continue;
            }
            float x1 = border.getX();
            float y1 = border.getY();
            float width = border.getWidth();
            float height = border.getHeight();
            float x2 = x1 + width;
            float y2 = y1 + height;
            Color color = Color.black;
            RenderUtils.drawRect(x1,y1,x1 + 1,y2,color);
            RenderUtils.drawRect(x2 - 1,y1,x2,y2,color);
            RenderUtils.drawRect(x1,y1,x2,y1 + 1,color);
            RenderUtils.drawRect(x1,y2 - 1,x2,y2,color);
            {
                boolean dragging = MouseUtils.isLeftClickDown() && MouseUtils.isHovered(x1,y1,x2,y2,mouseX,mouseY);
                drag(mouseX,mouseY,dragging,true);
                element.setX(element.getX() + moveX);
                element.setY(element.getY() + moveY);
                drag(mouseX,mouseY,dragging,false);
            }
        }
    }

    private HUDManager getManager() {
        return Client.instance.hudManager;
    }

    private List<Element> getElements() {
        return getManager().getElements();
    }

    private void drag(int mouseX, int mouseY,boolean dragging,boolean pre) {
        if(lastX != Float.MAX_VALUE && lastY != Float.MAX_VALUE) {
            moveX = mouseX - lastX;
            moveY = mouseY - lastY;
        }
        lastX = mouseX;
        lastY = mouseY;
        if(!dragging) {
            lastX = Float.MAX_VALUE;
            lastY = Float.MAX_VALUE;
            if(!pre) {
                moveX = 0;
                moveY = 0;
            }
        }
    }

    private float getDefault() {
        return Float.MAX_VALUE;
    }
}
