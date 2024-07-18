package com.github.fengye.starring.uranium.listenable.module.impl.move;

import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.impl.SlowDownEvent;
import com.github.fengye.starring.uranium.api.event.impl.motion.MotionEvent;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.*;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.listenable.module.impl.move.noslow.NoSlowMode;
import com.github.fengye.starring.uranium.listenable.module.impl.move.noslow.impl.SwitchItemMode;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import net.minecraft.item.ItemStack;

@ModuleInfo(name = "NoSlow",category = Category.Movement)
public class NoSlow extends Module {
    private final ModeValue modeValue = new ModeValue("Mode",Modes.values(),Modes.Vanilla);
    private final OptionValue blockValue = new OptionValue("Block",true);
    private final OptionValue consumeValue = new OptionValue("Consume",true);
    private final OptionValue bowValue = new OptionValue("Bow",true);
    private final NumberValue blockStrafeValue = new NumberValue("BlockStrafe", 1,0,1,0.1);
    private final NumberValue blockForwardValue = new NumberValue("BlockForward",1,0,1,0.1);
    private final NumberValue consumeStrafeValue = new NumberValue("ConsumeStrafe",1,0,1,0.1);
    private final NumberValue consumeForwardValue = new NumberValue("ConsumeForward",1,0,1,0.1);
    private final NumberValue bowStrafeValue = new NumberValue("BowStrafe",1,0,1,0.1);
    private final NumberValue bowForwardValue = new NumberValue("BowForward",1,0,1,0.1);

    @EventHandle
    private void onSlowDown(SlowDownEvent event) {
        if (!effectiveItem()) {
            return;
        }
        ItemStack stack = C09Utils.getHeldItem();
        if(isHolding(stack,true,false,false)) {
            event.setStrafe(blockStrafeValue.get().floatValue());
            event.setForward(blockForwardValue.get().floatValue());
        } else if(isHolding(stack,false,true,false)) {
            event.setStrafe(consumeStrafeValue.get().floatValue());
            event.setForward(consumeForwardValue.get().floatValue());
        } else if(isHolding(stack,false,false,true)) {
            event.setStrafe(bowStrafeValue.get().floatValue());
            event.setForward(bowForwardValue.get().floatValue());
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
        if(effectiveItem()) {
            getBypassMode().onMotion(event);
        }
    }

    private boolean effectiveItem() {
        ItemStack stack = C09Utils.getHeldItem();
        boolean block = isHolding(stack,true,false,false) && blockValue.get();
        boolean consume = isHolding(stack,false,true,false) && consumeValue.get();
        boolean bow = isHolding(stack,false,false,true) && bowValue.get();
        return block || consume || bow;
    }

    @Override
    public void updateAddedValues() {
        for (Modes mode : Modes.values()) {
            for (Value<?> value : JavaUtils.getValues(mode.getMode())) {
                addValue(mode,value);
            }
        }
    }

    private enum Modes {
        Vanilla(new NoSlowMode()),
        SwitchItem(new SwitchItemMode());

        private final NoSlowMode mode;

        Modes(NoSlowMode mode) {
            this.mode = mode;
        }

        public NoSlowMode getMode() {
            return mode;
        }
    }
}
