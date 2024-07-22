package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.ui.hud.GuiHUDEdit;

@ModuleInfo(name = "HUDEditGui",category = Category.Render,canEnable = false)
public class HUDEditGui extends Module {
    @Override
    public void onEnable() {
        mc.displayGuiScreen(new GuiHUDEdit());
    }
}
