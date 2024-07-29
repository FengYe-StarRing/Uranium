package com.github.fengye.starring.uranium.ui.gui.base;

import com.github.fengye.starring.uranium.ui.buttons.BorderlessButton;
import com.github.fengye.starring.uranium.ui.hud.element.Horizontal;
import com.github.fengye.starring.uranium.ui.hud.element.Side;
import com.github.fengye.starring.uranium.ui.hud.element.Vertical;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen extends GuiScreen {
    private final GuiScreen parentScreen;
    private final List<Integer> goBackButtonIDList = new ArrayList<>();

    public BaseScreen() {
        parentScreen = null;
    }

    public BaseScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    public GuiScreen getParentScreen() {
        return parentScreen;
    }

    public BorderlessButton getGoBackButton(Side side,boolean center) {
        String name = GuiButton.getButtonName("GoBack");
        Horizontal horizontal = side.getHorizontal();
        Vertical vertical = side.getVertical();
        if(horizontal == Horizontal.Left) {
            name = "<- " + name;
        } else {
            name = name + " ->";
        }
        BorderlessButton button = new BorderlessButton(-1,3,0, name);
        if(horizontal != Horizontal.Left) {
            button.xPosition = width - button.getButtonWidth() - 3;
        }
        if(vertical == Vertical.Up) {
            button.setAnimMode(BorderlessButton.AnimModes.TopLine);
        } else {
            button.yPosition = height - button.getButtonHeight();
        }
        if(center) {
            button.xPosition = width / 2 - button.getButtonWidth() / 2;
        }
        return button;
    }

    public void addGoBackButton(int id,Side side,boolean center) {
        BorderlessButton button = getGoBackButton(side,center);
        button.id = id;
        buttonList.add(button);
        goBackButtonIDList.add(id);
    }

    public void addGoBackButton(int id,boolean center) {
        addGoBackButton(id,Side.toDefault(),center);
    }

    public void addGoBackButton(int id,Side side) {
        addGoBackButton(id,side,false);
    }

    public void addGoBackButton(int id) {
        addGoBackButton(id,Side.toDefault(),false);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(Keyboard.KEY_ESCAPE == keyCode) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int id = button.id;
        for (Integer i : goBackButtonIDList) {
            if(id == i) {
                mc.displayGuiScreen(parentScreen);
            }
        }
        super.actionPerformed(button);
    }
}
