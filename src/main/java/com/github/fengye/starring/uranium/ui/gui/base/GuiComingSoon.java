package com.github.fengye.starring.uranium.ui.gui.base;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class GuiComingSoon extends BaseScreen {
    private static String render;

    public GuiComingSoon(GuiScreen screen) {
        super(screen);
    }

    @Override
    public void initGui() {
        addGoBackButton(0);
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        if(render == null) {
            render = Client.getText("ComingSoon") + "...";
        }
        FontRender font  = FontManager.alibaba48;
        font.drawCenteredString(render,width / 2F,height / 2F - font.getStringHeight(render) / 2F, Color.white);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
