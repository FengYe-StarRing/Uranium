package com.github.fengye.starring.uranium.ui.gui.game.clickgui.csgo;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.ArrayValue;
import com.github.fengye.starring.uranium.api.value.Numbers;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.api.value.impl.TextValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.mouse.DraggUtils;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import com.github.fengye.starring.uranium.utils.render.blur.BlurUtils;
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
    public static float startX = 20, startY = 20;
    public int moduleStart = 0;
    public int valueStart = 0;
    public Opacity opacity = new Opacity(255);
    public int opacityx = 255;
    public float moveX = 0, moveY = 0;
    boolean previousmouse = true;
    boolean mouse;

    private static final DraggUtils dragg = new DraggUtils();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRender font = FontManager.alibaba17;
        FontRenderer mcFont = FontManager.mcFont;

        dragg.setDragCondition(0,startX, startY - 25, startX + 400, startY + 25);
        dragg.drag(true);
        startX += dragg.getMoveX();
        startY += dragg.getMoveY();
        dragg.drag(false);

        this.opacity.interpolate((float) opacityx);
        {
            int height = 320;
            float radius = 20;
            {
                int width = 60;
                BlurUtils.drawBlurRect(startX, startY, width, height,radius);
//                float x2 = startX + width;
//                float y2 = startY + height;
//                RenderUtils.drawBorder(startX,startY,x2,y2,new Color(40, 40, 40, (int) opacity.getOpacity()));
            }
            {
                float x1 = startX + 60;
                float y1 = startY;
                int width = 140;
                BlurUtils.drawBlurRect(x1, y1, width, height,radius);
//                float x2 = x1 + width;
//                float y2 = y1 + height;
//                RenderUtils.drawBorder(x1,y2,x2,y2, new Color(31, 31, 31, (int) opacity.getOpacity()));
            }
            {
                float x1 = startX + 200;
                float y1 = startY;
                int width = 220;
                BlurUtils.drawBlurRect(x1, y1, width, height,radius);
//                float x2 = x1 + width;
//                float y2 = y1 + height;
//                RenderUtils.drawBorder(x1,y1,x2,y2,new Color(40, 40, 40, (int) opacity.getOpacity()));
            }
        }
        FontManager.harmony18.drawCenteredString(Client.getName() + " " + Client.getVersion(), startX + 30, startY + 4, -1);
        for (int i = 0; i < Category.values().length; i++) {
            Category[] iterator = Category.values();
            if (iterator[i] == currentModuleType) {
                R2DUtils.drawFilledCircle(startX + 30, startY + 30 + i * 40, 15, ColorUtils.transparent(255,255,255).getRGB(), 5);
            }
            RenderUtils.drawImage(iterator[i].getIcon(), (int) (startX + 20), (int) (startY + 20 + i * 40), 20, 20);
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

//                {
//                    float x1 = startX + 75;
//                    float y1 = mY;
//                    float x2 = startX + 185;
//                    float y2 = mY + 2;
//                    float width = x2 - x1;
//                    float height = y2 - y1;
//                    RenderUtils.drawRect(x1, y1, x2, y2, new Color(40, 40, 40, (int) opacity.getOpacity()).getRGB());
//                }
                mcFont.drawStringWithShadow(module.getName(), startX + 90, mY + 9,
                        new Color(248, 248, 248, (int) opacity.getOpacity()).getRGB());
                if (!module.isEnabled()) {
                    R2DUtils.drawFilledCircle(startX + 75, mY + 13, 2,
                            new Color(255, 0, 0, (int) opacity.getOpacity()).getRGB(), 5);
                } else {

                    R2DUtils.drawFilledCircle(startX + 75, mY + 13, 2,
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
                if(!value.isDisplay()) {
                    continue;
                }
                if (value instanceof Numbers) {
                    float x = startX + 300;
                    double render = 68.0F
                            * (((Number) value.get()).floatValue() - ((Numbers) value).getMin().floatValue())
                            / (((Numbers) value).getMax().floatValue()
                            - ((Numbers) value).getMin().floatValue());
                    RenderUtils.drawRect(x - 6, mY + 2, (float) ((double) x + 75), mY + 3,
                            (new Color(50, 50, 50, (int) opacity.getOpacity())).getRGB());
                    RenderUtils.drawRect(x - 6, mY + 2, (float) ((double) x + render + 6.5D), mY + 3,
                            (new Color(61, 141, 255, (int) opacity.getOpacity())).getRGB());
                    RenderUtils.drawRect((float) ((double) x + render + 2D), mY, (float) ((double) x + render + 7D),
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
                    RenderUtils.drawRect(x + 56, mY, x + 76, mY + 1,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    RenderUtils.drawRect(x + 56, mY + 8, x + 76, mY + 9,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    RenderUtils.drawRect(x + 56, mY, x + 57, mY + 9,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    RenderUtils.drawRect(x + 77, mY, x + 76, mY + 9,
                            new Color(255, 255, 255, (int) opacity.getOpacity()).getRGB());
                    if ((boolean) value.get()) {
                        RenderUtils.drawRect(x + 67, mY + 2, x + 75, mY + 7,
                                new Color(61, 141, 255, (int) opacity.getOpacity()).getRGB());
                    } else {
                        RenderUtils.drawRect(x + 58, mY + 2, x + 65, mY + 7,
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
                if (value instanceof ArrayValue) {
                    float x = startX + 300;
                    mcFont.drawStringWithShadow(value.getName(), startX + 210, mY, -1);
                    RenderUtils.drawRect(x - 5, mY - 5, x + 90, mY + 15,
                            new Color(56, 56, 56, (int) opacity.getOpacity()).getRGB());
                    R2DUtils.drawBorderedRect(x - 5, mY - 5, x + 90, mY + 15,
                            new Color(101, 81, 255, (int) opacity.getOpacity()).getRGB(), 2);
                    mcFont.drawStringWithShadow(((ArrayValue<?>) value).getAsString(),
                            x + 44 - mcFont.getStringWidth(((ArrayValue<?>) value).getAsString()) / 2F, mY + 1, -1);
                    {
                        float y1 = mY - 5;
                        int width = 100;
                        float x2 = x + width;
                        int height = 15;
                        float y2 = mY + height;
                        if (this.isStringHovered(x, y1, x2 - width / 2F, y2, mouseX, mouseY)) {
                            if (Mouse.isButtonDown(0) && !this.previousmouse) {
                                ((ArrayValue<?>) value).switchMode(true);
                                this.previousmouse = true;
                            }
                            if (!Mouse.isButtonDown(0)) {
                                this.previousmouse = false;
                            }
                        }
                        if (this.isStringHovered(x2 - width / 2F, y1, x2, y2, mouseX, mouseY)) {
                            if (Mouse.isButtonDown(0) && !this.previousmouse) {
                                ((ArrayValue<?>) value).switchMode(false);
                                this.previousmouse = true;
                            }
                            if (!Mouse.isButtonDown(0)) {
                                this.previousmouse = false;
                            }
                        }
                    }
                    mY += 25;
                }
                if (value instanceof TextValue) {
                    float x = startX + 300;
                    mcFont.drawStringWithShadow(value.getName(), startX + 210, mY, -1);
                    RenderUtils.drawRect(x - 5, mY - 5, x + 90, mY + 15,
                            new Color(56, 56, 56, (int) opacity.getOpacity()).getRGB());
                    R2DUtils.drawBorderedRect(x - 5, mY - 5, x + 90, mY + 15,
                            new Color(101, 81, 255, (int) opacity.getOpacity()).getRGB(), 2);
                    mcFont.drawStringWithShadow(((TextValue) value).get(), x + 44 - mcFont.getStringWidth(((TextValue) value).get()) / 2F, mY + 1, -1);
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

    @Override
    public void onGuiClosed() {
        this.opacity.setOpacity(0);
        this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/fxaa.json"));
    }

    private static class Opacity {
        private float opacity;
        private long lastMS;

        public Opacity(int opacity) {
            this.opacity = (float)opacity;
            this.lastMS = System.currentTimeMillis();
        }

        public void interpolate(float targetOpacity) {
            long currentMS = System.currentTimeMillis();
            long delta = currentMS - this.lastMS;
            this.lastMS = currentMS;
            this.opacity = AnimationUtil.calculateCompensation(targetOpacity, this.opacity, delta, 20);
        }

        public void interp(float targetOpacity, int speed) {
            long currentMS = System.currentTimeMillis();
            long delta = currentMS - this.lastMS;
            this.lastMS = currentMS;
            this.opacity = AnimationUtil.calculateCompensation(targetOpacity, this.opacity, delta, speed);
        }

        public float getOpacity() {
            return (float)((int)this.opacity);
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
        }
    }
}