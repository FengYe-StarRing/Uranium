/*
 * Decompiled with CFR 0.136.
 */
package com.github.fengye.starring.uranium.ui.clickgui.csgo;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.Numbers;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FastUniFontRenderer;
import com.github.fengye.starring.uranium.utils.render.R2DUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;

// 过渡ClickGui
// Skid by FoodTower

public class CSGOClickUI extends GuiScreen implements GuiYesNoCallback {
    public static Category currentModuleType = Category.Combat;
    public static Module currentModule = null;
    public static float startX = 40, startY = 40;
    public int moduleStart = 0;
    public int valueStart = 0;
    public Opacity opacity = new Opacity(255);
    public int opacityx = 255;
    public float moveX = 0, moveY = 0;
    boolean previousmouse = true;
    boolean mouse;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FastUniFontRenderer font = FontManager.harmony17;
        FontRenderer mcFont = FontManager.mcFont;
        if (isHovered(startX, startY - 25, startX + 400, startY + 25, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (moveX == 0 && moveY == 0) {
                moveX = mouseX - startX;
                moveY = mouseY - startY;
            } else {
                startX = mouseX - moveX;
                startY = mouseY - moveY;
            }
            this.previousmouse = true;
        } else if (moveX != 0 || moveY != 0) {
            moveX = 0;
            moveY = 0;
        }
        this.opacity.interpolate((float) opacityx);
        Gui.drawRect(startX, startY, startX + 60, startY + 320,
                new Color(40, 40, 40, (int) opacity.getOpacity()).getRGB());
        Gui.drawRect(startX + 60, startY, startX + 200, startY + 320,
                new Color(31, 31, 31, (int) opacity.getOpacity()).getRGB());
        Gui.drawRect(startX + 200, startY, startX + 420, startY + 320,
                new Color(40, 40, 40, (int) opacity.getOpacity()).getRGB());
		FontManager.harmony18.drawCenteredString(Client.getName() + " " + Client.getVersion(), startX + 30, startY + 4, -1);
		for (int i = 0; i < Category.values().length; i++) {
            Category[] iterator = Category.values();
            if (iterator[i] != currentModuleType) {
                RenderUtils.drawFilledCircle(startX + 30, startY + 30 + i * 40, 15,
                        new Color(56, 56, 56, (int) opacity.getOpacity()).getRGB(), 5);
            } else {
                RenderUtils.drawFilledCircle(startX + 30, startY + 30 + i * 40, 15,
                        new Color(101, 81, 255, (int) opacity.getOpacity()).getRGB(), 5);
            }
			RenderUtils.drawImage(new ResourceLocation(Client.RESOURCES + "/ClickIcons/" + iterator[i].name() + ".png"), (int) (startX + 20), (int) (startY + 20 + i * 40), 20, 20);
			try {
                if (this.isCategoryHovered(startX + 15, startY + 15 + i * 40, startX + 45, startY + 45 + i * 40, mouseX,
                        mouseY) && Mouse.isButtonDown(0)) {
                    currentModuleType = iterator[i];
                    currentModule = !Client.instance.moduleManager.getModulesInType(currentModuleType).isEmpty()
                            ? Client.instance.moduleManager.getModulesInType(currentModuleType).get(0)
                            : null;
                    moduleStart = 0;
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        int m = Mouse.getDWheel();
        if (this.isCategoryHovered(startX + 60, startY, startX + 200, startY + 320, mouseX, mouseY)) {
            if (m < 0 && moduleStart < Client.instance.moduleManager.getModulesInType(currentModuleType).size() - 1) {
                moduleStart++;
            }
            if (m > 0 && moduleStart > 0) {
                moduleStart--;
            }
        }
        if (this.isCategoryHovered(startX + 200, startY, startX + 420, startY + 320, mouseX, mouseY)) {
            if (m < 0 && valueStart < currentModule.getValues().size() - 1) {
                valueStart++;
            }
            if (m > 0 && valueStart > 0) {
                valueStart--;
            }
        }
        mcFont.drawStringWithShadow(
                currentModule == null ? currentModuleType.getName()
                        : currentModuleType.getName() + "/" + currentModule.getName(),
                startX + 70, startY + 15, new Color(248, 248, 248).getRGB());
        if(currentModule != null) {
            font.drawStringWithShadow(currentModule.getDescription(),startX + 210,startY + 15, new Color(248, 248, 248).getRGB());
        }
        if (currentModule != null) {
            float mY = startY + 30;
            for (int i = 0; i < Client.instance.moduleManager.getModulesInType(currentModuleType).size(); i++) {
                Module module = Client.instance.moduleManager.getModulesInType(currentModuleType).get(i);
                if (mY > startY + 300)
                    break;
                if (i < moduleStart) {
                    continue;
                }

                RenderUtils.drawRect2(startX + 75, mY, startX + 185, mY + 2,
                        new Color(40, 40, 40, (int) opacity.getOpacity()).getRGB());
                mcFont.drawStringWithShadow(module.getName(), startX + 90, mY + 9,
                        new Color(248, 248, 248, (int) opacity.getOpacity()).getRGB());
                if (!module.isEnabled()) {
                    RenderUtils.drawFilledCircle(startX + 75, mY + 13, 2,
                            new Color(255, 0, 0, (int) opacity.getOpacity()).getRGB(), 5);
                } else {

                    RenderUtils.drawFilledCircle(startX + 75, mY + 13, 2,
                            new Color(0, 255, 0, (int) opacity.getOpacity()).getRGB(), 5);
                }
                if (isSettingsButtonHovered(startX + 90, mY,
                        startX + 100 + (mcFont.getStringWidth(module.getName())),
                        mY + 8 + mcFont.FONT_HEIGHT, mouseX, mouseY)) {
                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
						module.setEnabled(!module.isEnabled());
                        previousmouse = true;
                    }
                    if (!this.previousmouse && Mouse.isButtonDown(1)) {
                        previousmouse = true;
                    }
                }

                if (!Mouse.isButtonDown(0)) {
                    this.previousmouse = false;
                }
                if (isSettingsButtonHovered(startX + 90, mY,
                        startX + 100 + (mcFont.getStringWidth(module.getName())),
                        mY + 8 + mcFont.FONT_HEIGHT, mouseX, mouseY) && Mouse.isButtonDown(1)) {
                    currentModule = module;
                    valueStart = 0;
                }
                mY += 25;
            }
            mY = startY + 30;
            for (int i = 0; i < currentModule.getValues().size(); i++) {
                if (mY > startY + 300)
                    break;
                if (i < valueStart) {
                    continue;
                }
                Value value = currentModule.getValues().get(i);
                if (value instanceof Numbers) {
                    float x = startX + 300;
                    double render = 68.0F
                            * (((Number) value.get()).floatValue() - ((Numbers) value).getMin().floatValue())
                            / (((Numbers) value).getMax().floatValue()
                            - ((Numbers) value).getMin().floatValue());
                    RenderUtils.drawRect2(x - 6, mY + 2, (float) ((double) x + 75), mY + 3,
                            (new Color(50, 50, 50, (int) opacity.getOpacity())).getRGB());
                    RenderUtils.drawRect2(x - 6, mY + 2, (float) ((double) x + render + 6.5D), mY + 3,
                            (new Color(61, 141, 255, (int) opacity.getOpacity())).getRGB());
                    RenderUtils.drawRect2((float) ((double) x + render + 2D), mY, (float) ((double) x + render + 7D),
                            mY + 5, (new Color(61, 141, 255, (int) opacity.getOpacity())).getRGB());
                    mcFont.drawStringWithShadow(value.getName() + ": " + value.get(), startX + 210, mY, -1);
                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }
                    if (this.isButtonHovered(x, mY - 2, x + 100, mY + 7, mouseX, mouseY)
                            && Mouse.isButtonDown(0)) {
                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                            render = ((Numbers) value).getMin().doubleValue();
                            double max = ((Numbers) value).getMax().doubleValue();
                            double inc = ((Numbers) value).getInc().doubleValue();
                            double valAbs = (double) mouseX - ((double) x + 1.0D);
                            double perc = valAbs / 68.0D;
                            perc = Math.min(Math.max(0.0D, perc), 1.0D);
                            double valRel = (max - render) * perc;
                            double val = render + valRel;
                            val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
                            value.set(Double.valueOf(val));
                        }
                        if (!Mouse.isButtonDown(0)) {
                            this.previousmouse = false;
                        }
                    }
                    mY += 20;
                }
                if (value instanceof OptionValue) {
                    float x = startX + 300;
                    mcFont.drawStringWithShadow(value.getName(), startX + 210, mY, -1);
                    RenderUtils.drawRect2(x + 56, mY, x + 76, mY + 1,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    RenderUtils.drawRect2(x + 56, mY + 8, x + 76, mY + 9,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    RenderUtils.drawRect2(x + 56, mY, x + 57, mY + 9,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    RenderUtils.drawRect2(x + 77, mY, x + 76, mY + 9,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    if ((boolean) value.get()) {
                        RenderUtils.drawRect2(x + 67, mY + 2, x + 75, mY + 7,
                                new Color(61, 141, 255, (int) opacity.getOpacity()).getRGB());
                    } else {
                        RenderUtils.drawRect2(x + 58, mY + 2, x + 65, mY + 7,
                                new Color(150, 150, 150, (int) opacity.getOpacity()).getRGB());
                    }
                    mcFont.drawStringWithShadow(value.getName(), startX + 210, mY, -1);
                    Gui.drawRect(x + 56, mY, x + 76, mY + 1, new Color(255, 255, 255).getRGB());
                    Gui.drawRect(x + 56, mY + 8, x + 76, mY + 9, new Color(255, 255, 255).getRGB());
                    Gui.drawRect(x + 56, mY, x + 57, mY + 9, new Color(255, 255, 255).getRGB());
                    Gui.drawRect(x + 77, mY, x + 76, mY + 9, new Color(255, 255, 255).getRGB());
                    if ((boolean) value.get()) {
                        Gui.drawRect(x + 67, mY + 2, x + 75, mY + 7,
                                new Color(61, 141, 255).getRGB());
                    } else {
                        Gui.drawRect(x + 58, mY + 2, x + 65, mY + 7,
                                new Color(150, 150, 150).getRGB());
                    }
                    if (this.isCheckBoxHovered(x + 56, mY, x + 76, mY + 9, mouseX, mouseY)) {
                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                            this.previousmouse = true;
                            this.mouse = true;
                        }

                        if (this.mouse) {
                            value.set(!(boolean) value.get());
                            this.mouse = false;
                        }
                    }
                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }
                    mY += 20;
                }
                if (value instanceof ModeValue) {
                    float x = startX + 300;
                    mcFont.drawStringWithShadow(value.getName(), startX + 210, mY, -1);
                    RenderUtils.drawRect2(x - 5, mY - 5, x + 90, mY + 15,
                            new Color(56, 56, 56, (int) opacity.getOpacity()).getRGB());
                    R2DUtils.drawBorderedRect(x - 5, mY - 5, x + 90, mY + 15,
                            new Color(101, 81, 255, (int) opacity.getOpacity()).getRGB(), 2);
                    mcFont.drawStringWithShadow(((ModeValue) value).getAsString(),
							x + 44 - mcFont.getStringWidth(((ModeValue) value).getAsString()) / 2F, mY + 1, -1);
                    if (this.isStringHovered(x, mY - 5, x + 100, mY + 15, mouseX, mouseY)) {
                        if (Mouse.isButtonDown(0) && !this.previousmouse) {
                            ((ModeValue) value).switchMode(false);
                            this.previousmouse = true;
                        }
                        if (!Mouse.isButtonDown(0)) {
                            this.previousmouse = false;
                        }

                    }
                    mY += 25;
                }
            }
        }

    }

    public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
		return mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2;
	}

    public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
	}

    public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
		return mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2;
	}

    public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
		return mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2;
	}

    public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
	}

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
	}

    public void initGui() {
        this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        this.opacity.setOpacity(0);
        this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/fxaa.json"));
    }
}
