package com.github.fengye.starring.uranium.ui.gui.base;

import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

// Skid by FoodTower

public final class Splash {
    // Max amount of progress updates
    private static final int DEFAULT_MAX = 2;
    // Current progress
    private static int PROGRESS;
    // Background texture
    // Texture manager
    public static TextureManager ctm;

    private static FontRender font;

    /**
     * Update the splash text
     */
    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) return;
        drawSplash();
    }

    /**
     * Update the splash progress
     *
     * @param givenProgress Stage displayed on the splash
     */
    public static void setProgress(int givenProgress) {
        PROGRESS = givenProgress;
        update();
    }

    public static void drawSplash() {
        // Get the users screen width and height to apply
        final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());

        // Create the scale factor
        final int scaleFactor = scaledresolution.getScaleFactor();

        // Bind the width and height to the framebuffer
        final Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * scaleFactor,
                scaledresolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);

        // Create the projected image to be rendered
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        // Draw the progress bar
        drawProgress();

        // Unbind the width and height as it's no longer needed
        framebuffer.unbindFramebuffer();

        // Render the previously used frame buffer
        framebuffer.framebufferRender(scaledresolution.getScaledWidth() * scaleFactor, scaledresolution.getScaledHeight() * scaleFactor);

        // Update the texture to enable alpha drawing
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

        // Update the users screen
        Minecraft.getMinecraft().updateDisplay();
    }

    /**
     * Render the progress bar and text
     */
    private static void drawProgress() {
        if (Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null) {
            return;
        }

        // Get the users screen width and height to apply
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        // Get the wanted x position
        final float startX = (sr.getScaledWidth() / 2.0F - 55);
        final float endX = (sr.getScaledWidth() / 2.0F + 55);

        // Calculate the progress bar
        final double nProgress = PROGRESS;
        final double calc = (nProgress / DEFAULT_MAX) * 110;

        {
            float y1 = sr.getScaledHeight() / 2.0F + 15.0F;
            // Draw the transparent bar before the green bar
            Gui.drawRect(startX, y1, endX, sr.getScaledHeight() / 2.0F + 20.0F, new Color(255, 255, 255, 60).getRGB());

            // Render the blue progress bar
            Gui.drawRect(startX, y1, (float) (startX + calc), sr.getScaledHeight() / 2.0F + 20.0F, new Color(255, 255, 255, 60).getRGB());
        }

        String text = "Welcome to Uranium!";

        // Render the rise text
        if(font == null) {
            font = FontManager.loadFont("RobotoBold.ttf",30);
        }
        final float width = font.getStringWidth(text);
        final float height = font.getStringHeight(text);

        final float x = (sr.getScaledWidth() / 2.0F) - (width / 2.0F);
        final float y = (sr.getScaledHeight() / 2.0F) - (height / 2.0F) - 5;

        font.drawString(text, x, y, new Color(0, 0, 0, 120).getRGB());
    }
}

