package com.github.fengye.starring.uranium.listenable.module.impl.render;

import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.ui.clickgui.ClientClickGui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGui",category = Category.Render,keyBind = Keyboard.KEY_RSHIFT,canEnable = false)
public class ClickGui extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Client);

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Modes.valueOf(modeValue.getAsString()).newInstance());
    }

    private enum Modes {
        Client(ClientClickGui.class);

        private final Class<? extends GuiScreen> gui;

        Modes(Class<? extends GuiScreen> gui) {
            this.gui = gui;
        }

        public GuiScreen newInstance() {
            try {
                return gui.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
    }
}
