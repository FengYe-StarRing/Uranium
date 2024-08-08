package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.impl.ColorValue;
import com.github.fengye.starring.uranium.api.value.impl.FontValue;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.special.Palette;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.ui.hud.element.*;
import com.github.fengye.starring.uranium.utils.render.AnimationUtils;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import com.github.fengye.starring.uranium.utils.render.blur.BlurUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arraylist extends Element {
    private final FontValue fontValue = new FontValue("Font", FontManager.alibaba20);
    private final NumberValue intervalValue = new NumberValue("Interval",0,0,6,1);
    private final NumberValue background_spaceValue = new NumberValue("Background-Space",2,0,6,1);
    private final ModeValue background_modeValue = new ModeValue("Background-Mode", BackgroundModes.values(), BackgroundModes.Rect);
    private final ModeValue background_colorModeValue = new ModeValue("Background-ColorMode", ColorModes.values(), ColorModes.Rainbow);
    private final ColorValue background_redValue = new ColorValue("Background-Red",0);
    private final ColorValue background_greenValue = new ColorValue("Background-Green",0);
    private final ColorValue background_blueValue = new ColorValue("Background-Blue",0);
    private final ColorValue background_alphaValue = new ColorValue("Background-Alpha",80);
    private static final ModeValue text_caseValue = new ModeValue("Text-Case",CaseModes.values(),CaseModes.None);
    private final ModeValue text_colorModeValue = new ModeValue("Text-ColorMode", ColorModes.values(), ColorModes.Rainbow);
    private final ColorValue text_redValue = new ColorValue("Text-Red",0);
    private final ColorValue text_greenValue = new ColorValue("Text-Green",111);
    private final ColorValue text_blueValue = new ColorValue("Text-Blue",255);
    private final ColorValue text_alphaValue = new ColorValue("Text-Alpha",255);
    private static final ModeValue tag_styleValue = new ModeValue("Tag-Style", TagStyleModes.values(), TagStyleModes.None);
    private static final ModeValue tag_caseValue = new ModeValue("Tag-Case",CaseModes.values(),CaseModes.None);
    private final ModeValue tag_colorModeValue = new ModeValue("Tag-ColorMode", ColorModes.values(), ColorModes.Custom);
    private final ColorValue tag_redValue = new ColorValue("Tag-Red",255);
    private final ColorValue tag_greenValue = new ColorValue("Tag-Green",255);
    private final ColorValue tag_blueValue = new ColorValue("Tag-Blue",255);
    private final ColorValue tag_alphaValue = new ColorValue("Tag-Alpha",255);
    private final ModeValue border_modeValue = new ModeValue("Border-Mode",BorderModes.values(),BorderModes.None);
    private final ModeValue border_leftModeValue = new ModeValue("Border-LeftMode",BorderLeftModes.values(),BorderLeftModes.Rect);
    private final NumberValue border_leftIntervalValue = new NumberValue("Border-LeftInterval",0,0,6,1);
    private final NumberValue border_leftWidthValue = new NumberValue("Border-LeftWidth",1,0,8,1);
    private final ModeValue border_colorModeValue = new ModeValue("Border-ColorMode",ColorModes.values(),ColorModes.Rainbow);
    private final ColorValue border_redValue = new ColorValue("Border-Red",255);
    private final ColorValue border_greenValue = new ColorValue("Border-Green",0);
    private final ColorValue border_blueValue = new ColorValue("Border-Blue",0);
    private final ColorValue border_alphaValue = new ColorValue("Border-Alpha",255);

    private static final List<ModuleData> drawModules = new ArrayList<>();
    private FontRender font;
    private Border border;

    public Arraylist() {
        super("Arraylist", 3, 3,new Side(Horizontal.Right, Vertical.Up));
        setLock(true);
    }

    @Override
    public Border render() {
        lock(0,0);
        update();
        draw();
        return border;
    }

    private void update() {
        Side side = getSide();
        Horizontal horizontal = side.getHorizontal();
        Vertical vertical = side.getVertical();
        List<ModuleData> removes = new ArrayList<>();
        for (Module module : Client.instance.moduleManager.getModules()) {
            if(module.isEnabled()) {
                if(!ModuleData.find(module)) {
                    ModuleData newMod = new ModuleData(module);
                    drawModules.add(newMod);
                }
            } else {
                if(ModuleData.find(module)) {
                    removes.add(ModuleData.get(module));
                }
            }
        }
        for (ModuleData remove : removes) {
            drawModules.remove(remove);
        }
        // 更新位置
        font = fontValue.get();
        int startX = 0,startY = 0,maxWidth = 0,maxHeight = 0;
        int drawY = startY;
        for (ModuleData drawModule : drawModules) {
            String drawString = drawModule.getDrawString();
            int rectWidth = font.getStringWidth(drawString);
            int rectHeight = font.getStringHeight(drawString) + background_spaceValue.get().intValue();
            int width = rectWidth;
            if(border_modeValue.get().equals(BorderModes.Left) || border_modeValue.get().equals(BorderModes.Full)) {
                if(border_modeValue.get().equals(BorderModes.Full)) {
                    width += 2;
                } else {
                    width += (int) (border_leftIntervalValue.get() + border_leftWidthValue.get());
                }
            } else if(border_modeValue.get().equals(BorderModes.Right) || border_modeValue.get().equals(BorderModes.Full)) {
                width += 1;
            }
            int height = rectHeight;
            drawModule.rectWidth = rectWidth;
            drawModule.rectHeight = rectHeight;
            drawModule.width = width;
            drawModule.height = height;
        }
        if(vertical.equals(Vertical.Up)) {
            drawModules.sort((a1, a2) -> a2.width - a1.width);
        } else {
            drawModules.sort((a2, a1) -> a1.width - a2.width);
        }
        int index = 0;
        for (ModuleData drawModule : drawModules) {
            if(!drawModule.init) {
                drawModule.x = startX;
                drawModule.y = drawY;
                drawModule.init = true;
            }
            int targetX = startX;
            int targetY = drawY;
            int x = drawModule.x;
            int y = drawModule.y;
            int rectWidth = drawModule.rectWidth;
            int rectHeight = drawModule.rectHeight;
            int width = drawModule.width;
            int height = drawModule.height;
            x = AnimationUtils.move(x,targetX,rectWidth);
            y = AnimationUtils.move(y,targetY,rectHeight);
            drawModule.x = x;
            drawModule.y = y;
            drawModule.index = index;
            drawY += (height + intervalValue.get().intValue()) * (vertical.equals(Vertical.Up) ? 1 : -1);
            if(width > maxWidth) {
                maxWidth = width;
            }
            maxHeight += height;
            index++;
        }
        {
            int width = maxWidth;
            int height = maxHeight;
            if(!drawModules.isEmpty()) {
                height += (int) ((drawModules.size() - 1) * intervalValue.get());
            }
            border = new Border(startX - (horizontal.equals(Horizontal.Left) ? 0 : width),startY - (vertical.equals(Vertical.Up) ? 0 : height),width,height);
        }
        // 上色
        Palette.RainbowSpeeds rainbowMode = Palette.RainbowSpeeds.Normal;
        Palette.RainbowSpeeds rectRainbowMode = Palette.RainbowSpeeds.Normal;
        Palette.RainbowSpeeds tagRainbowMode = Palette.RainbowSpeeds.Normal;
        Palette.RainbowSpeeds borderRainbowMode = Palette.RainbowSpeeds.Normal;
        float hue = Palette.getHue(rainbowMode);
        float rectHue = Palette.getHue(rectRainbowMode);
        float tagHue = Palette.getHue(tagRainbowMode);
        float borderHue = Palette.getHue(borderRainbowMode);
        for (ModuleData drawModule : drawModules) {
            drawModule.color = getRenderColor(text_colorModeValue, text_redValue, text_greenValue, text_blueValue, text_alphaValue,hue);
            hue += Palette.getRainbowSpeed(rainbowMode);
            drawModule.rectColor = getRenderColor(background_colorModeValue, background_redValue, background_greenValue, background_blueValue, background_alphaValue,rectHue);
            rectHue += Palette.getRainbowSpeed(rectRainbowMode);
            drawModule.tagColor = getRenderColor(tag_colorModeValue,tag_redValue,tag_greenValue,tag_blueValue,tag_alphaValue,tagHue);
            tagHue += Palette.getRainbowSpeed(tagRainbowMode);
            drawModule.borderColor = getRenderColor(border_colorModeValue,border_redValue,border_greenValue,border_blueValue,border_alphaValue,borderHue);
            borderHue += Palette.getRainbowSpeed(borderRainbowMode);
        }
    }

    private void draw() {
        Side side = getSide();
        Horizontal horizontal = side.getHorizontal();
        Vertical vertical = side.getVertical();
        ModuleData lastModule = null;
        for (ModuleData module : drawModules) {
            int rectWidth = module.rectWidth;
            int rectHeight = module.rectHeight;
            int width = module.width;
            int height = module.height;
            int startX = horizontal.equals(Horizontal.Left) ? module.x : module.x - width;
            int startY = module.y - (vertical.equals(Vertical.Up) ? 0 : height);
            int drawX = startX;
            int index = module.index;
            if(horizontal.equals(Horizontal.Left) && (border_modeValue.get().equals(BorderModes.Left) || border_modeValue.get().equals(BorderModes.Full))) {
                int x1 = drawX;
                int x2 = x1 + border_leftWidthValue.get().intValue();
                if(border_modeValue.get().equals(BorderModes.Full)) {
                    x2 = x2 - border_leftWidthValue.get().intValue() + 1;
                    RenderUtils.drawRect(x1,startY,x2,startY + rectHeight,module.borderColor);
                } else {
                    switch ((BorderLeftModes)border_leftModeValue.get()) {
                        case Rect:
                            RenderUtils.drawRect(x1,startY,x2,startY + rectHeight,module.borderColor);
                            break;
                        case RoundRect:
                            RenderUtils.drawRoundRect(x1,startY,x2,startY + rectHeight,4,module.borderColor);
                            break;
                    }
                }
                drawX += x2 - drawX + border_leftIntervalValue.get().intValue();
                if(border_modeValue.get().equals(BorderModes.Full)) {
                    drawX -= border_leftIntervalValue.get().intValue();
                }
            }
            if(border_modeValue.get().equals(BorderModes.Right) || border_modeValue.get().equals(BorderModes.Full)) {
                switch (horizontal) {
                    case Left: {
                        int x1 = drawX + rectWidth;
                        int x2 = x1 + 1;
                        RenderUtils.drawRect(x1,startY,x2,startY + rectHeight,module.borderColor);
                        break;
                    }
                    case Right: {
                        int x1 = drawX;
                        int x2 = x1 + 1;
                        RenderUtils.drawRect(x1,startY,x2,startY + rectHeight,module.borderColor);
                        drawX += x2 - x1;
                        break;
                    }
                }
            }
            switch ((BackgroundModes) background_modeValue.get()) {
                case Rect:
                    RenderUtils.drawRect(drawX,startY,drawX + rectWidth,startY + rectHeight, module.rectColor);
                    break;
                case BlurRect:
                    BlurUtils.drawBlurRect(drawX,startY,rectWidth,rectHeight);
                    break;
                case Shadow:
                    RenderUtils.drawShadow(drawX,startY,rectWidth,rectHeight);
                    break;
                case RoundRect:
                    RenderUtils.drawRoundRect(drawX,startY,drawX + rectWidth,startY + rectHeight, 8,module.rectColor);
                    break;
            }
            {
                int drawY = startY + rectHeight / 2 - font.getStringHeight(module.getDrawString()) / 2;
                font.drawString(module.getName(),drawX,drawY,module.color);
                drawX += font.getStringWidth(module.getName());
                if(module.getTag() != null && !module.getTag().isEmpty()) {
                    font.drawString(module.getSeparate(),drawX,drawY,module.separateColor);
                    drawX += font.getStringWidth(module.getSeparate());
                    font.drawString(module.getTag(),drawX,drawY,module.tagColor);
                    drawX = startX + rectWidth;
                }
            }
            {
                if(border_modeValue.get().equals(BorderModes.Top) || border_modeValue.get().equals(BorderModes.Full)) {
                    if(index == 0) {
                        int x1 = startX,y1 = startY,x2 = startX + rectWidth;
                        if(vertical.equals(Vertical.Up)) {
                            int y2 = startY + 1;
                            if(border_modeValue.get().equals(BorderModes.Full)) {
                                x2 += 1;
                            }
                            RenderUtils.drawRect(x1,y1,x2,y2,module.borderColor);
                        } else {
                            y1 = startY + rectHeight - 1;
                            int y2 = startY + rectHeight;
                            if(border_modeValue.get().equals(BorderModes.Full)) {
                                x2 += 1;
                            }
                            RenderUtils.drawRect(x1,y1,x2,y2,module.borderColor);
                        }
                    }
                }
                if(border_modeValue.get().equals(BorderModes.Bottom) || border_modeValue.get().equals(BorderModes.Full)) {
                    if(index == drawModules.size() - 1) {
                        if(vertical.equals(Vertical.Up)) {
                            int x1 = startX,y1 = startY + rectHeight - 1,x2 = x1 + rectWidth,y2 = startY + rectHeight;
                            if(border_modeValue.get().equals(BorderModes.Full)) {
                                x2 += 1;
                            }
                            RenderUtils.drawRect(x1,y1,x2,y2,module.borderColor);
                        } else {
                            int x1 = startX,y1 = startY - 1,x2 = x1 + rectWidth,y2 = startY;
                            if(border_modeValue.get().equals(BorderModes.Full)) {
                                x2 += 2;
                                if(horizontal.equals(Horizontal.Right) && vertical.equals(Vertical.Down)) {
                                    x2 -= 1;
                                }
                            }
                            RenderUtils.drawRect(x1,y1,x2,y2,module.borderColor);
                        }
                    }
                }
            }
            if(horizontal.equals(Horizontal.Right) && (border_modeValue.get().equals(BorderModes.Left) || border_modeValue.get().equals(BorderModes.Full))) {
                int x1 = drawX + border_leftIntervalValue.get().intValue();
                int x2 = x1 + border_leftWidthValue.get().intValue();
                if(border_modeValue.get().equals(BorderModes.Full)) {
                    x1 -= border_leftIntervalValue.get().intValue();
                    x2 = x1 + 1;
                    RenderUtils.drawRect(x1,startY,x2,startY + rectHeight,module.borderColor);
                } else {
                    switch ((BorderLeftModes)border_leftModeValue.get()) {
                        case Rect:
                            RenderUtils.drawRect(x1,startY,x2,startY + rectHeight,module.borderColor);
                            break;
                        case RoundRect:
                            RenderUtils.drawRoundRect(x1,startY,x2,startY + rectHeight,4,module.borderColor);
                            break;
                    }
                }
            }
            if(border_modeValue.get().equals(BorderModes.Full) && lastModule != null) {
                int lastRectWidth = lastModule.rectWidth;
                int x1 = startX + rectWidth + 1;
                int x2 = x1 + lastRectWidth - rectWidth + 1;
                if(horizontal.equals(Horizontal.Right)) {
                    x1 = startX - (lastRectWidth - width) - 2;
                    x2 = x1 + lastRectWidth - rectWidth;
                }
                int y1 = startY;
                if(vertical.equals(Vertical.Up)) {
                    int y2 = startY + 1;
                    RenderUtils.drawRect(x1,y1,x2,y2,module.borderColor);
                } else {
                    y1 += rectHeight - 1;
                    int y2 = y1 + 1;
                    RenderUtils.drawRect(x1,y1,x2,y2,module.borderColor);
                }
            }
            lastModule = module;
        }
    }

    private Color getRenderColor(ModeValue value, int r, int g, int b, int a, float hue) {
        Color color;
        switch ((ColorModes)value.get()) {
            case Rainbow:
                color = ColorUtils.getRainbowColor(hue);
                color = new Color(color.getRed(),color.getGreen(),color.getBlue(),a);
                break;
            default:
            case Custom:
                color = new Color(r,g,b,a);
                break;
        }
        return color;
    }

    private Color getRenderColor(ModeValue value, ColorValue r, ColorValue g, ColorValue b, ColorValue a, float hue) {
        return getRenderColor(value,r.getValue(),g.getValue(),b.getValue(),a.getValue(),hue);
    }

    private static class ModuleData {
        private final Module module;
        private int x,y,width,height,rectWidth,rectHeight;
        private boolean init;
        private Color color,rectColor,tagColor,separateColor,borderColor;
        private int index = 0;

        public ModuleData(Module module) {
            this.module = module;
            init = false;
            separateColor = Color.gray;
        }

        private static boolean find(Module module) {
            for (ModuleData drawModule : drawModules) {
                if(drawModule.module.equals(module)) {
                    return true;
                }
            }
            return false;
        }

        private static ModuleData get(Module module) {
            for (ModuleData drawModule : drawModules) {
                if(drawModule.module.equals(module)) {
                    return drawModule;
                }
            }
            return null;
        }

        private String getName() {
            String name = module.getName();
            switch ((CaseModes)text_caseValue.get()) {
                case Lower:
                    name = name.toLowerCase();
                    break;
                case Upper:
                    name = name.toUpperCase();
                    break;
            }
            return name;
        }

        private String getTag() {
            String tag = module.getTag();
            switch ((CaseModes)tag_caseValue.get()) {
                case Lower:
                    tag = tag.toLowerCase();
                    break;
                case Upper:
                    tag = tag.toUpperCase();
                    break;
            }
            return tag;
        }

        private String getSeparate() {
            String separate;
            switch ((TagStyleModes)tag_styleValue.get()) {
                case Vertical:
                    separate = " | ";
                    break;
                default:
                case None:
                    separate = " ";
                    break;
            }
            return separate;
        }

        private String getDrawString() {
            return getName() + (getTag() != null && !getTag().isEmpty() ? getSeparate() + getTag() : '\0');
        }
    }

    private enum ColorModes {
        Rainbow,Custom
    }

    private enum BackgroundModes {
        Rect,BlurRect,Shadow,RoundRect
    }

    private enum BorderModes {
        Full,Left,Right,Top,Bottom,None
    }

    private enum BorderLeftModes {
        Rect,RoundRect
    }

    private enum TagStyleModes {
        None,Vertical
    }

    private enum CaseModes {
        None,Lower,Upper
    }
}
