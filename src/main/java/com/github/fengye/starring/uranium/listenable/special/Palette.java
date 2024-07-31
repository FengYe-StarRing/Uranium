package com.github.fengye.starring.uranium.listenable.special;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.impl.TickEvent;
import com.github.fengye.starring.uranium.api.value.impl.ColorValue;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.render.ColorUtils;

import java.awt.*;

public class Palette implements Listenable {
    public static final ColorValue r1 = new ColorValue("Red",0);
    public static final ColorValue g1 = new ColorValue("Green",255);
    public static final ColorValue b1 = new ColorValue("Blue",255);
    public static final ColorValue a1 = new ColorValue("Alpha",255);
    public static final ColorValue r2 = new ColorValue("Red2",255);
    public static final ColorValue g2 = new ColorValue("Green2",0);
    public static final ColorValue b2 = new ColorValue("Blue2",0);
    public static final ColorValue a2 = new ColorValue("Alpha2",255);
    private static final NumberValue rainbowSpeed1 = new NumberValue("RainbowSpeed1",3,1,24,1);
    private static final NumberValue rainbowSpeed2 = new NumberValue("RainbowSpeed2",6,1,24,1);
    private static final NumberValue rainbowSpeed3 = new NumberValue("RainbowSpeed2",12,1,24,1);

    private static float hue1 = 0;
    private static float hue2 = 0;
    private static float hue3 = 0;

    private static Color rainbow1 = ColorUtils.getRainbowColor(hue1);
    private static Color rainbow2 = ColorUtils.getRainbowColor(hue2);
    private static Color rainbow3 = ColorUtils.getRainbowColor(hue3);

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle
    private void onTick(TickEvent event) {
        hue1 = ColorUtils.addHue(hue1,getRainbowSpeed(RainbowSpeeds.Slow));
        hue2 = ColorUtils.addHue(hue2,getRainbowSpeed(RainbowSpeeds.Normal));
        hue3 = ColorUtils.addHue(hue2,getRainbowSpeed(RainbowSpeeds.Fast));
        rainbow1 = ColorUtils.getRainbowColor(hue1);
        rainbow2 = ColorUtils.getRainbowColor(hue2);
        rainbow3 = ColorUtils.getRainbowColor(hue3);
    }

    public static float getHue(RainbowSpeeds mode) {
        float ret = 0;
        switch (mode) {
            case Slow:
                ret =  hue1;
                break;
            case Normal:
                ret =  hue2;
                break;
            case Fast:
                ret =  hue3;
                break;
            default:
                return ret;
        }
        return ret / MinecraftInstance.timer.timerSpeed;
    }

    public static Color getRainbow(RainbowSpeeds mode) {
        switch (mode) {
            case Slow:
                return rainbow1;
            case Normal:
                return rainbow2;
            case Fast:
                return rainbow3;
            default:
                return getColor1();
        }
    }

    public static Color getColor1() {
        return new Color(r1.get().intValue(),g1.get().intValue(),b1.get().intValue(),a1.get().intValue());
    }

    public static Color getColor2() {
        return new Color(r2.get().intValue(),g2.get().intValue(),b2.get().intValue(),a2.get().intValue());
    }

    public static float getRainbowSpeed(RainbowSpeeds mode) {
        switch (mode) {
            case Slow:
                return rainbowSpeed1.get().floatValue();
            case Normal:
                return rainbowSpeed2.get().floatValue();
            case Fast:
                return rainbowSpeed3.get().floatValue();
            default:
                return 0;
        }
    }

    public enum RainbowSpeeds {
        Slow,Normal,Fast
    }
}
