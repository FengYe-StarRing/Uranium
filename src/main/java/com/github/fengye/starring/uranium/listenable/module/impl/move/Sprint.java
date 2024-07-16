package com.github.fengye.starring.uranium.listenable.module.impl.move;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.impl.UpdateEvent;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;

@ModuleInfo(name = "Sprint",category = Category.Movement)
public class Sprint extends Module {
    @EventHandle
    private void onUpdate(UpdateEvent event) {
        mc.gameSettings.keyBindSprint.pressed = true;
    }
}
