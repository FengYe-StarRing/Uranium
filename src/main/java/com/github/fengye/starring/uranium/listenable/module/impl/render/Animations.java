package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;

@ModuleInfo(name = "Animations",category = Category.Render)
public class Animations extends Module {
    public static final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.v1_8);

    public static void block(float f,float f1) {
        switch ((Animations.Modes)Animations.modeValue.get()) {
            case v1_8:
                transformFirstPersonItem(f, 0.0F);
                doBlockTransformations();
                break;
            case Leaked: {
                float swingScale = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.141592653589793F) / 8;
                GlStateManager.translate(0.1, 0.03, 0.0);
                transformFirstPersonItem(f, 0);
                doBlockTransformations();
                GlStateManager.scale(0.8 + swingScale, 0.8 + swingScale, 0.8 + swingScale);
                GlStateManager.rotate(-MathHelper.sin((float) (MathHelper.sqrt_float(f1) * Math.PI)) * 20.0F, 0.0F, 1.2F, -0.8f);
                GlStateManager.rotate(-MathHelper.sin((float) (MathHelper.sqrt_float(f1) * Math.PI)) * 30.0F, 1.0F, 0F, 0.0f);
                break;
            }
        }
    }

    private static void transformFirstPersonItem(float equipProgress, float swingProgress) {
        getItemRenderer().transformFirstPersonItem(equipProgress,swingProgress);
    }

    private static ItemRenderer getItemRenderer() {
        return mc.getItemRenderer();
    }

    private static void doBlockTransformations() {
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    public enum Modes {
        v1_8,Leaked
    }
}
