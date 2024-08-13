package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;

@ModuleInfo(name = "FovWiden",category = Category.Render)
public class FovWiden extends Module {
    private final NumberValue fovValue = new NumberValue("Widen",31,0,32,1);

    private float oldFov = 1;

    @Override
    public void onEnable() {
        oldFov = gameSettings.fovSetting;
    }

    @Override
    public void onDisable() {
        gameSettings.fovSetting = oldFov;
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        gameSettings.fovSetting = 110 + fovValue.get().floatValue();
    }
}
