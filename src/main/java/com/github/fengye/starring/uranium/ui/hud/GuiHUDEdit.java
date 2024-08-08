package com.github.fengye.starring.uranium.ui.hud;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.impl.HUDManager;
import com.github.fengye.starring.uranium.ui.hud.element.Border;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import com.github.fengye.starring.uranium.utils.render.blur.BlurUtils;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.List;

public class GuiHUDEdit extends GuiScreen {
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
            RenderUtils.drawBorder(x1,y1,x2,y2,color);
            {
                element.dragg.setDragCondition(0,x1,y1,x2,y2);
                element.dragg.drag(mouseX,mouseY,true);
                element.setX(element.getX() + element.dragg.getMoveX());
                element.setY(element.getY() + element.dragg.getMoveY());
                element.dragg.drag(mouseX,mouseY,false);
            }
        }
    }

    private HUDManager getManager() {
        return Client.instance.hudManager;
    }

    private List<Element> getElements() {
        return getManager().getElements();
    }
}
