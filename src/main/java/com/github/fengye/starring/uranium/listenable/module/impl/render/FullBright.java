package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;

@ModuleInfo(name = "FullBright",category = Category.Render)
public class FullBright extends Module {
    private final NumberValue gammaValue = new NumberValue("Gamma",2,-2,2,0.5);

    private float oldGamma = 1;

    @Override
    public void onEnable() {
        oldGamma = gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        gameSettings.gammaSetting = oldGamma;
    }

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        gameSettings.gammaSetting = gammaValue.get().floatValue();
    }
}
