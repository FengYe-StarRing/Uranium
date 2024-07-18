package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.ui.clickgui.csgo.CSGOClickUI;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGui",category = Category.Render,keyBind = Keyboard.KEY_RSHIFT,canEnable = false)
public class ClickGui extends Module {
    @Override
    public void onEnable() {
        mc.displayGuiScreen(new CSGOClickUI());
    }
}
