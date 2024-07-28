package com.github.fengye.starring.uranium.utils.render;

import java.awt.*;

public class ColorUtils {
    public static Color getRainbowColor(float hue) {
        while (hue > 255) {
            hue -= 255;
        }
        return Color.getHSBColor(hue / 255.0F, 0.55F, 0.9F);
    }

    public static float addHue(float hue,float speed) {
        return hue + speed;
    }

    public static Color transparent(int r,int g,int b,int transparency) {
        return new Color(r,g,b,transparency * 80);
    }

    public static Color transparent(int r,int g,int b) {
        return transparent(r,g,b,1);
    }

    public static Color transparent() {
        return transparent(0,0,0);
    }
}
