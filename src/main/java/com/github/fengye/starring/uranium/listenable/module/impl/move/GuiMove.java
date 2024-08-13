package com.github.fengye.starring.uranium.listenable.module.impl.move;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.UpdateEvent;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(name = "GuiMove",category = Category.Movement)
public class GuiMove extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Vanilla);

    @EventHandle
    private void onUpdate(UpdateEvent event) {
        GuiScreen screen = currentScreen;
        if(screen != null && !(screen instanceof GuiChat)) {
            gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(gameSettings.keyBindForward);
            gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(gameSettings.keyBindBack);
            gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(gameSettings.keyBindLeft);
            gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(gameSettings.keyBindRight);
            gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(gameSettings.keyBindJump);
            gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(gameSettings.keyBindSneak);
        }
    }

    private enum Modes {
        Vanilla
    }
}
