package com.github.fengye.starring.uranium.ui.gui.base;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import com.github.fengye.starring.uranium.utils.render.blur.BlurUtils;
import net.minecraft.client.gui.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GuiLogin extends GuiScreen implements GuiYesNoCallback {
    private static boolean login = false;

    private int shadowX;
    private int shadowY;
    private int shadowWidth;
    private int shadowHeight;
    private int startX;
    private int startY;

    private GuiTextField inputUsername,inputPassword;
    private static String inputUsernameStandby,inputPasswordStandby;

    @Override
    public void initGui() {
        if(login) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
        shadowX = width / 6;
        shadowY = height / 6;
        shadowWidth = width - shadowX * 2;
        shadowHeight = height - shadowY * 2;
        int buttonIntervalX = shadowWidth / 48;
        int buttonIntervalY = 4;
        startX = shadowX + buttonIntervalX;
        startY = shadowY + buttonIntervalY;
        int buttonHeight = 20;
        int buttonY = shadowY + shadowHeight - buttonIntervalY - (buttonHeight + buttonIntervalY) * 3;
        {
            int width = shadowWidth - shadowWidth / 2;
            FontRenderer mcFont = FontManager.mcFont;
            inputUsername = new GuiTextField(0,mcFont,startX,buttonY,width,buttonHeight) {
                @Override
                public void drawTextBox() {
                    String text = getText();
                    if(inputUsernameStandby == null) {
                        inputUsernameStandby = Client.getText("Username") + "...";
                    }
                    setText(text.isEmpty() ? inputUsernameStandby : text);
                    super.drawTextBox();
                    setText(text);
                }
            };
            buttonY += buttonHeight + buttonIntervalY;
            inputPassword = new GuiTextField(1,mcFont,startX,buttonY,width,buttonHeight) {
                @Override
                public void drawTextBox() {
                    String text = getText();
                    if(inputPasswordStandby == null) {
                        inputPasswordStandby = Client.getText("Password") + "...";
                    }
                    setText(text.isEmpty() ? inputPasswordStandby : text.replaceAll("(.)","●"));
                    super.drawTextBox();
                    setText(text);
                }
            };
            buttonY += buttonHeight + buttonIntervalY;
        }
        {
            int startX = this.startX;
            GuiButton button = new GuiButton(0,startX,buttonY,true,true,GuiButton.getButtonName("Exit"));
            buttonList.add(button);
            startX += button.getButtonWidth() + buttonIntervalX;
            buttonList.add(new GuiButton(1,startX,buttonY,true,true,GuiButton.getButtonName("Login")));
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        BlurUtils.drawBlurRect(0,0,width,height,5);
        RenderUtils.drawShadow(shadowX,shadowY,shadowWidth,shadowHeight,3);
        {
            int imageX = shadowX + shadowWidth / 2;
            int imageSideLength = shadowWidth / 2;
            int imageY = shadowY + shadowHeight - imageSideLength;
            RenderUtils.drawImage(MinecraftInstance.getResourceLocation("/Gui/0.png"),imageX,imageY,imageSideLength,imageSideLength);
        }
        for (GuiTextField guiTextField : getGuiTextFields()) {
            guiTextField.drawTextBox();
        }
        {
            int startX = this.startX;
            int startY = this.startY;
            FontRender nameFont = FontManager.robotoBold48;
            FontRender versionFont = FontManager.alibaba18;
            String clientName = Client.getName();
            String version = Client.getVersion();
            nameFont.drawString(clientName,startX,startY,Color.white.getRGB());
            startX += nameFont.getStringWidth(clientName);
            startY += nameFont.getStringHeight(clientName) - versionFont.getStringHeight(version);
            versionFont.drawString(version,startX,startY,Color.white.getRGB());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.shutdown();
                break;
            case 1:
                login = true;
                mc.displayGuiScreen(new GuiMainMenu());
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (GuiTextField guiTextField : getGuiTextFields()) {
            guiTextField.textboxKeyTyped(typedChar,keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiTextField guiTextField : getGuiTextFields()) {
            guiTextField.mouseClicked(mouseX,mouseY,mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private List<GuiTextField> getGuiTextFields() {
        return JavaUtils.getGuiTextFields(this);
    }
}
