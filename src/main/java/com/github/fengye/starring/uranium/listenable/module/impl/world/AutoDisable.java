package com.github.fengye.starring.uranium.listenable.module.impl.world;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.WorldChangeEvent;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.KillAura;
import net.minecraft.client.Minecraft;

@ModuleInfo(name = "AutoDisable",category = Category.World)
public class AutoDisable extends Module {
    private final ModeValue killAuraValue = new ModeValue("KillAura",Modes.values(),Modes.Disable);

    private KillAura killAura;

    @EventHandle
    private void onWorldChange(WorldChangeEvent event) {
        if(killAura == null) {
            killAura = (KillAura) Client.instance.moduleManager.getModule(KillAura.class);
        }
        if(killAura.isEnabled()) {
            switch ((Modes)killAuraValue.get()) {
                case Disable:
                    killAura.setEnabled(false);
                    break;
                case ReEnable:
                    killAura.setEnabled(false);
                    killAura.setEnabled(true);
                    break;
                case None:
                    break;
            }
        }
    }

    private enum Modes {
        Disable,ReEnable,None
    }
}
