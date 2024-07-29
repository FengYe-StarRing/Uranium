package com.github.fengye.starring.uranium.utils.render.blur;

import com.github.fengye.starring.uranium.utils.render.RenderUtils;

import java.awt.*;

public class BlurUtils {

    public static void drawBlurRect(float x, float y, float width, float height,float radius) {
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x, y, x + width, y + height, new Color(0).getRGB());
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(radius);
        StencilUtil.uninitStencilBuffer();
    }

    public static void drawBlurRect(float x, float y, float width, float height) {
        drawBlurRect(x,y,width,height,10);
    }

    public static void drawBlurRoundRect(float x, float y, float width, float height,float radius,int blurRadius) {
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundRect(x, y, x + width, y + height, radius, new Color(-2));
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(blurRadius);
        StencilUtil.uninitStencilBuffer();
    }

    public static void drawBlurRoundRect(float x, float y, float width, float height,float radius) {
        drawBlurRoundRect(x,y,width,height,radius,8);
    }

    public static void drawShadowBlur(float x, float y, float width, float height,float radius,int whileNumber) {
        drawBlurRect(x,y,width,height,radius);
        RenderUtils.drawShadow(x,y,width,height,whileNumber);
    }

    public static void drawShadowBlur(float x, float y, float width, float height) {
        drawShadowBlur(x,y,width,height,10,1);
    }

    public static void drawShadowBlur(float x, float y, float width, float height,int whileNumber) {
        drawShadowBlur(x,y,width,height,10,whileNumber);
    }
}
