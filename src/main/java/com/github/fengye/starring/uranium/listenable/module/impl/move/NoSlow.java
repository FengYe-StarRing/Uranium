package com.github.fengye.starring.uranium.listenable.module.impl.move;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.impl.SlowDownEvent;
import com.github.fengye.starring.uranium.api.event.impl.motion.MotionEvent;
import com.github.fengye.starring.uranium.api.value.impl.FloatValue;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.move.noslow.NoSlowMode;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import net.minecraft.item.ItemStack;

@ModuleInfo(name = "NoSlow",category = Category.Movement)
public class NoSlow extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Vanilla);
    private final FloatValue blockStrafeValue = new FloatValue("BlockStrafe",1F,0F,1F,0.1F);
    private final FloatValue blockForwardValue = new FloatValue("BlockForward",1F,0F,1F,0.1F);
    private final FloatValue consumeStrafeValue = new FloatValue("ConsumeStrafe",1F,0F,1F,0.1F);
    private final FloatValue consumeForwardValue = new FloatValue("ConsumeForward",1F,0F,1F,0.1F);
    private final FloatValue bowStrafeValue = new FloatValue("BowStrafe",1F,0F,1F,0.1F);
    private final FloatValue bowForwardValue = new FloatValue("BowForward",1F,0F,1F,0.1F);

    @EventHandle
    private void onSlowDown(SlowDownEvent event) {
        ItemStack stack = C09Utils.getHeldItem();
        if(isHolding(stack,true,false,false)) {
            event.setStrafe(blockStrafeValue.get());
            event.setForward(blockForwardValue.get());
        } else if(isHolding(stack,false,true,false)) {
            event.setStrafe(consumeStrafeValue.get());
            event.setForward(consumeForwardValue.get());
        } else if(isHolding(stack,false,false,true)) {
            event.setStrafe(bowStrafeValue.get());
            event.setForward(bowForwardValue.get());
        }
    }

    private boolean isHolding(ItemStack stack,boolean sword,boolean con,boolean bow) {
        return NoSlowMode.isHolding(stack,sword,con,bow);
    }

    private NoSlowMode getBypassMode() {
        return Modes.valueOf(modeValue.getAsString()).getMode();
    }

    @EventHandle
    private void onMotion(MotionEvent event) {
        getBypassMode().onMotion(event);
    }

    private enum Modes {
        Vanilla(new NoSlowMode());

        private final NoSlowMode mode;

        Modes(NoSlowMode mode) {
            this.mode = mode;
        }

        public NoSlowMode getMode() {
            return mode;
        }
    }
}
