package com.github.fengye.starring.uranium.utils.render.blur;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniform1;

public class GaussianBlur {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static ShaderUtil blurShader = new ShaderUtil(Client.RESOURCES + "/Shaders/gaussian.frag");

    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static void rendershadow(int sourceTexture, int radius, int offset) {
        framebuffer = createFrameBuffer(framebuffer);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; ++i)
            weightBuffer.put(calculateGaussianValue(i, radius));
    }

    public static void setupUniforms(float dir1, float dir2, float radius) {
        blurShader.setUniformi("textureIn", 0);
        blurShader.setUniformf("texelSize", 1.0F / (float) Minecraft.getMinecraft().displayWidth, 1.0F / (float) Minecraft.getMinecraft().displayHeight);
        blurShader.setUniformf("direction", dir1, dir2);
        blurShader.setUniformf("radius", radius);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(calculateGaussianValue(i, radius / 2));
        }

        weightBuffer.rewind();
        glUniform1(blurShader.getUniform("weights"), weightBuffer);
    }


    public static void renderBlur(float radius) {
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 1);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);


        framebuffer = createFrameBuffer(framebuffer);

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        setupUniforms(1, 0, radius);

        bindTexture(Minecraft.getMinecraft().getFramebuffer().framebufferTexture);

        ShaderUtil.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        setupUniforms(0, 1, radius);

        bindTexture(framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        blurShader.unload();

        RenderUtils.resetColor();
        GlStateManager.bindTexture(0);
    }

    public static void bindTexture(int texture) {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        }
        return framebuffer;
    }
}
