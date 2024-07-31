package net.minecraft.client.gui;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.special.Palette;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.utils.mouse.MouseUtils;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;
import com.github.fengye.starring.uranium.utils.render.RenderUtils;
import com.github.fengye.starring.uranium.utils.render.blur.BlurUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /** Button width in pixels */
    protected int width;

    /** Button height in pixels */
    protected int height;

    /** The x position of this control. */
    public int xPosition;

    /** The y position of this control. */
    public int yPosition;

    /** The string displayed on this control. */
    public String displayString;
    public int id;

    /** True if this control is enabled, false to disable. */
    public boolean enabled;

    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;

    private final FontRender font = FontManager.alibaba18;

    public GuiButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public GuiButton(int buttonId, int x, int y, boolean autoWidth,boolean autoHeight, String buttonText) {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
        this.width = autoWidth ? font.getStringWidth(buttonText) + 8 : 200;
        this.height = autoHeight ? 20 : 20;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            int x1 = xPosition;
            int y1 = yPosition;
            int width = this.width;
            int height = this.height;
            int x2 = xPosition + width;
            int y2 = yPosition + height;

            BlurUtils.drawShadowBlur(x1,y1,width,height);
            Color rectColor = this.enabled ? Palette.getColor1() : Palette.getColor2();
            RenderUtils.drawBorder(x1,y1,x2,y2,ColorUtils.transparent(rectColor));

            if(MouseUtils.isHovered(x1,y1,x2,y2)) {
                RenderUtils.drawRect(x1,y1,x2,y2,ColorUtils.transparent(255,255,255));
            }

            this.mouseDragged(mc, mouseX, mouseY);

            font.drawCenteredString(displayString,x1 + width / 2F,y1 + height / 2F - font.getStringHeight(displayString) / 2F, Color.white.getRGB());
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public static String getButtonName(String name) {
        return Client.instance.languageManager.getTranslate("Ui.Gui.Button." + name);
    }

    public FontRender getFont() {
        return font;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getButtonHeight() {
        return this.height;
    }
}
