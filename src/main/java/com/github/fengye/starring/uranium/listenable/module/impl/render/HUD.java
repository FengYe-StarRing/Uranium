package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.Render2DEvent;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.entity.MovementUtils;
import com.github.fengye.starring.uranium.utils.math.MathUtils;
import com.github.fengye.starring.uranium.utils.render.ScreenUtils;

import java.awt.*;

@ModuleInfo(name = "HUD",category = Category.Render)
public class HUD extends Module {
    private final OptionValue elementValue = new OptionValue("Element",true);
    private final OptionValue infoValue = new OptionValue("Info",true);

    public HUD() {
        setEnabled(true);
    }

    @EventHandle
    private void onRender2D(Render2DEvent event) {
        if(elementValue.get()) {
            Client.instance.hudManager.render();
        }
        if(infoValue.get()) {
            drawInfo();
        }
    }

    private void drawInfo() {
        FontRender font = FontManager.baloo24;
        String position = "XYZ: " + (int)thePlayer.posX + ' ' + (int)thePlayer.posY + ' ' + (int)thePlayer.posZ;
        String speed = "Speed: " + MathUtils.keepDecimalPoint(MovementUtils.getMoveSpeed(),3);
        String fps = "FPS: " + getFPS();
        int startY = ScreenUtils.getHeight() - font.getStringHeight(position);
        int startX = 3;

        Color color1 = Color.white;
        Color color2 = Color.lightGray;
        {
            int x = startX;
            String[] strings = position.split(":");
            strings[0] += ":";
            font.drawStringWithShadow(strings[0],x,startY, color1);
            x += font.getStringWidth(strings[0]);
            font.drawStringWithShadow(strings[1],x,startY, color2);
        }
        startY -= font.getStringHeight(speed);
        {
            int x = startX;
            String[] strings = speed.split(":");
            strings[0] += ":";
            font.drawStringWithShadow(strings[0],x,startY, color1);
            x += font.getStringWidth(strings[0]);
            font.drawStringWithShadow(strings[1],x,startY, color2);
        }
        startY -= font.getStringHeight(fps);
        {
            int x = startX;
            String[] strings = fps.split(":");
            strings[0] += ":";
            font.drawStringWithShadow(strings[0],x,startY, color1);
            x += font.getStringWidth(strings[0]);
            font.drawStringWithShadow(strings[1],x,startY, color2);
        }
    }
}
