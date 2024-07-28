package com.github.fengye.starring.uranium.utils.render;

import com.github.fengye.starring.uranium.Client;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.mc;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {
    public static void drawRect(float x,float y,float x2,float y2,int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;

        float width = x2 - x;
        float height = y2 - y;

        glEnable(3042);
        glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);

        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(7);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glEnd();
        GL11.glPopMatrix();

        glEnable(3553);
        glDisable(3042);
        glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void drawRect(float x, float y, float x2, float y2, Color color) {
        drawRect(x,y,x2,y2,color.getRGB());
    }

    public static void glColor(int hex) {
        glColor(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, hex >> 24 & 0xFF);
    }

    public static void glColor(int red,int green,int blue,int alpha) {
        GL11.glColor4f(red / 255F, green / 255F, blue / 255F, alpha / 255F);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
        glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.enableBlend();
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(1F, 1F, 1f, alpha);
        mc.getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        resetColor();
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawImage(String image, int x, int y, int width, int height,float alpha) {
        drawImage(new ResourceLocation(Client.RESOURCES + image),x,y,width,height,alpha);
    }

    public static void drawImage(String image, int x, int y, int width, int height) {
        drawImage(new ResourceLocation(Client.RESOURCES + image),x,y,width,height,255);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        drawImage(image,x,y,width,height,255);
    }

    public static void drawShadow(float x,float y,float width,float height) {
        drawTexturedRect(x - 9.0f, y - 9.0f, 9.0f, 9.0f, "paneltopleft");
        drawTexturedRect(x - 9.0f, y + height, 9.0f, 9.0f, "panelbottomleft");
        drawTexturedRect(x + width, y + height, 9.0f, 9.0f, "panelbottomright");
        drawTexturedRect(x + width, y - 9.0f, 9.0f, 9.0f, "paneltopright");
        drawTexturedRect(x - 9.0f, y, 9.0f, height, "panelleft");
        drawTexturedRect(x + width, y, 9.0f, height, "panelright");
        drawTexturedRect(x, y - 9.0f, width, 9.0f, "paneltop");
        drawTexturedRect(x, y + height, width, 9.0f, "panelbottom");
    }

    public static void drawShadow(float x,float y,float width,float height,Color color) {
        drawShadow(x,y,width,height);
        drawRect(x,y,x + width,y + height,color);
    }

    public static void drawTexturedRect(float x,float y,float width,float height,String image) {
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled(3042);
        boolean disableAlpha = !GL11.glIsEnabled(3008);
        if (!enableBlend) {
            GL11.glEnable(3042);
        }
        if (!disableAlpha) {
            GL11.glDisable(3008);
        }
        mc.getTextureManager().bindTexture(new ResourceLocation(Client.RESOURCES + "/Shaders/" + image + ".png"));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }
        if (!disableAlpha) {
            GL11.glEnable(3008);
        }
        GL11.glPopMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(double x, double y, double u, double v, float width, float height, float textureWidth, float textureHeight) {
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) u, (float) v, (int) width, (int) height,textureWidth,textureHeight);
    }

    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }

    public static void drawBorder(float x1,float y1,float x2,float y2,boolean top,boolean bottom,boolean left,boolean right,Color topColor,Color bottomColor,Color leftColor,Color rightColor) {
        int width = 1;
        if(left) {
            drawRect(x1,y1,x1 + width,y2,leftColor);
        }
        if(right) {
            drawRect(x2 - width,y1,x2,y2,rightColor);
        }
        if(top) {
            drawRect(x1,y1,x2,y1 + width,topColor);
        }
        if(bottom) {
            drawRect(x1,y2 - width,x2,y2,bottomColor);
        }
    }

    public static void drawBorder(float x1,float y1,float x2,float y2,Color color) {
        drawBorder(x1,y1,x2,y2,true,true,true,true,color,color,color,color);
    }

    public static void drawRound(float x, float y, float x1, float y1, float radius, Color color) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        glDisable(GL11.GL_TEXTURE_2D);
        glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        setColor(color.getRGB());
        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        GL11.glEnd();
        glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_LINE_SMOOTH);
        glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void setColor(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    public static void setColor(int color) {
        setColor(color, (float) (color >> 24 & 255) / 255.0F);
    }
}
