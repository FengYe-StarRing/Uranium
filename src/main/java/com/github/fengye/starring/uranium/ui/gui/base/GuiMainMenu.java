package com.github.fengye.starring.uranium.ui.gui.base;

import com.github.fengye.starring.uranium.ui.buttons.BorderlessButton;
import net.minecraft.client.gui.*;

import java.io.IOException;

public class GuiMainMenu extends BaseScreen implements GuiYesNoCallback {
    @Override
    public void initGui() {
        int id = 0;
        {
            String[] strings = new String[]{"SinglePlayer","MultiPlayer","AltManager","Option","Exit"};
            int buttonIntervalX = width / (strings.length + 1);
            int startX = buttonIntervalX;
            for (String string : strings) {
                BorderlessButton button = new BorderlessButton(id, 0,0, GuiButton.getButtonName(string));
                button.xPosition = startX - button.getButtonWidth() / 2;
                button.yPosition = height - button.getButtonHeight();
                buttonList.add(button);
                startX += buttonIntervalX;
                id++;
            }
        }
        {
            String[] strings = new String[]{"ClientSetting","UpdateLogs"};
            int buttonIntervalX = width / (strings.length + 1);
            int startX = buttonIntervalX;
            for (String string : strings) {
                BorderlessButton button = new BorderlessButton(id, 0,0, GuiButton.getButtonName(string), BorderlessButton.AnimModes.TopLine);
                button.xPosition = startX - button.getButtonWidth() / 2;
                button.yPosition = 0;
                buttonList.add(button);
                startX += buttonIntervalX;
                id++;
            }
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 3:
                mc.displayGuiScreen(new GuiOptions(this,mc.gameSettings));
                break;
            case 4:
                mc.shutdown();
                break;
            case 2:
            case 5:
                mc.displayGuiScreen(new GuiComingSoon(this));
                break;
            case 6:
                mc.displayGuiScreen(new GuiUpdateLogs());
                break;
        }
        super.actionPerformed(button);
    }
}
