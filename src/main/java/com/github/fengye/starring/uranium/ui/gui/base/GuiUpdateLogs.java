package com.github.fengye.starring.uranium.ui.gui.base;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.special.Palette;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.mouse.ScrollBarUtils;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;
import com.github.fengye.starring.uranium.utils.mouse.DraggUtils;
import com.github.fengye.starring.uranium.utils.render.blur.BlurUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiUpdateLogs extends BaseScreen {
    private static String[] logs = null;
    private static int shadowX;
    private static int shadowY;
    private static int shadowWidth;
    private static int shadowHeight;
    private static boolean needInit = true;

    private static final FontRender font = FontManager.alibaba18;
    private static final ScrollBarUtils scrollBar = new ScrollBarUtils();
    private final DraggUtils dragg = new DraggUtils();

    @Override
    public void initGui() {
        if(logs == null) {
            initLogs();
        }
        shadowWidth = getLogsWidth();
        shadowHeight = getShadowHeight();
        if(needInit) {
            initShadow();
        } else if((shadowX + shadowWidth >= width || shadowY + shadowHeight >= height) || (shadowX + shadowWidth <= 0 || shadowY + shadowHeight <= 0)) {
            initShadow();
        }
        scrollBar.set(getValidIndex(),getIndex());
        addGoBackButton(0);
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        {
            BlurUtils.drawShadowBlur(shadowX,shadowY,shadowWidth,shadowHeight,3);
            dragg.setDragCondition(0,shadowX,shadowY,shadowX + shadowWidth,shadowY + shadowHeight);
            dragg.drag(true);
            shadowX += dragg.getMoveX();
            shadowY += dragg.getMoveY();
            dragg.drag(false);
        }
        int startY = shadowY;
        int startX = shadowX;
        Palette.RainbowSpeeds rainbowMode = Palette.RainbowSpeeds.Normal;
        float hue = Palette.getHue(rainbowMode);
        boolean rainbow = false;
        int index = 0;
        int index2 = 0;
        for (String log : logs) {
            String outLog = log;
            Color color = Color.white;
            List<String> tags = analysis(log);
            if(index < scrollBar.getUsedIndex()) {
                boolean flag = false;
                for (String tag : tags) {
                    if(tag.equals("NoNewLine")) {
                        flag = true;
                        break;
                    }
                }
                if(flag) {
                    continue;
                }
                index++;
                continue;
            }
            if(index2 > scrollBar.getValidIndex()) {
                break;
            }
            boolean noNewLine = false;
            for (String tag : tags) {
                boolean needBreak = false;
                boolean needDelete = false;
                switch (tag) {
                    case "Text":
                        needDelete = true;
                        needBreak = true;
                        break;
                    case "NoNewLine":
                        needDelete = true;
                        noNewLine = true;
                        break;
                    case "Rainbow":
                        needDelete = true;
                        rainbow = true;
                        break;
                    case "StopRainbow":
                        needDelete = true;
                        rainbow = false;
                        break;
                    case "+":
                        color = Color.green;
                        break;
                    case "-":
                        color = Color.yellow;
                        break;
                    case "!":
                        color = Color.red;
                        break;
                    case "~":
                        color = Color.cyan;
                        break;
                }
                if(needDelete) {
                    outLog = replaceAll(outLog,tag);
                }
                if(needBreak) {
                    break;
                }
            }
            if(!rainbow) {
                font.drawString(outLog,startX,startY,color);
            } else {
                for (char c : outLog.toCharArray()) {
                    font.drawChar(c,startX,startY,ColorUtils.getRainbowColor(hue));
                    startX += font.getCharWidth(c);
                    hue = ColorUtils.addHue(hue,Palette.getRainbowSpeed(rainbowMode));
                }
            }
            if(!noNewLine) {
                startY += font.getStringHeight(log);
                startX = shadowX;
                index2++;
            } else {
                if(!rainbow) {
                    startX += font.getStringWidth(outLog);
                }
            }
        }
        scrollBar.update();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void initLogs() {
        logs = new String[] {
                "[NoNewLine]铀的开发人员: ",
                "[Rainbow][NoNewLine]" + Client.getAuthor(),
                "[StopRainbow],more...",
                "[Text][+]新增,[-]删减,[~]修改或修复,[!]重大信息",
                "<当前>",
                "[!]请遵守GPL3.0开源协议,特别禁止花雨庭圈钱狗魔改闭源圈钱行为,否则你将受到惩罚!",
                "[!]请合法使用本程序,我们不承担任何因用户未遵守使用协议或法律规定所造成的风险",
                "[+]更多绕过",
                "[+]更多视觉",
                "[-]删减更多不必要的代码",
                "[~]优化",
                "[~]修复更多程序错误(包括原版客户端)",
                "[~]其他...",
                "<1.0>",
                "[NoNewLine]Hello ",
                "[Rainbow][NoNewLine]2024",
                "[StopRainbow]!"
        };
        /* *
        * [Rainbow] 渐变
        * [StopRainbow] 停止渐变
        * [NoNewLine] 不换行
        * [Text] 停止解析(纯文本)
        *  */
    }

    private int getLogsWidth() {
        int width = 0;
        for (String log : logs) {
            int w = font.getStringWidth(log);
            if(w > width) {
                width = w;
            }
        }
        return width;
    }

    private int getShadowHeight() {
        return this.height - this.height / 3;
    }

    private void initShadow() {
        shadowX = width / 2 - shadowWidth / 2;
        shadowY = height / 6;
        needInit = false;
    }

    private List<String> analysis(String log) {
        List<String> contents = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(log);
        while (matcher.find()) {
            contents.add(matcher.group(1));
        }
        return contents;
    }

    private String replaceAll(String log,String delete) {
        return log.replaceAll("\\[" + delete + "\\]","");
    }

    private int getValidIndex() {
        int height = shadowHeight;
        int h = 0;
        int index = 0;
        for (String log : logs) {
            h += font.getStringHeight(log);
            if(h >= height) {
                break;
            }
            index++;
        }
        return index;
    }

    private int getIndex() {
        int index = 0;
        for (String log : logs) {
            List<String> tags = analysis(log);
            boolean flag = false;
            for (String tag : tags) {
                if(tag.equals("NoNewLine")) {
                    flag = true;
                    break;
                }
            }
            if(flag) {
                continue;
            }
            index++;
        }
        return index;
    }
}
