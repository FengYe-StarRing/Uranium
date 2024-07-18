package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.impl.Render2DEvent;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;

@ModuleInfo(name = "HUD",category = Category.Render)
public class HUD extends Module {
    private final OptionValue elementValue = new OptionValue("Element",true);

    @EventHandle
    private void onRender2D(Render2DEvent event) {
        if(elementValue.get()) {
            Client.instance.hudManager.render();
        }
    }
}
