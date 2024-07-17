package com.github.fengye.starring.uranium.listenable.module.impl.move;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.impl.SlowDownEvent;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;

@ModuleInfo(name = "NoSlow",category = Category.Movement)
public class NoSlow extends Module {
    @EventHandle
    private void onSlowDown(SlowDownEvent event) {
        System.out.println("SlowDown");
    }
}
