package com.github.fengye.starring.uranium.listenable.module.impl.combat;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.game.TickEvent;
import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;
import com.github.fengye.starring.uranium.utils.entity.MovementUtils;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import com.github.fengye.starring.uranium.utils.packet.UseUtils;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.Random;

@ModuleInfo(name = "AutoClicker",category = Category.Combat)
public class AutoClicker extends Module {
    private final NumberValue cpsValue = new NumberValue("CPS",12,1,20,1);
    private final OptionValue leftValue = new OptionValue("Left",true);
    private final OptionValue blockHitValue = new OptionValue("BlockHit",false);
    private final OptionValue jitterValue = new OptionValue("Jitter",false);

    private final Timer clickTimer = new Timer();
    private boolean click = false;

    @Override
    public void onEnable() {
        click = false;
        clickTimer.reset();
    }

    @EventHandle
    private void onEvent(Event event) {
        if(leftValue.get() && gameSettings.keyBindAttack.isKeyDown() && clickTimer.hasTimePassed(getClickDelay()) && !UseUtils.isUsingItem()) {
            click = true;
            clickTimer.reset();
        }
    }

    private long getClickDelay() {
        return (long) (1000 / cpsValue.get());
    }

    @Override
    public String getTag() {
        return "CPS-" + cpsValue.get().intValue();
    }

    @EventHandle
    private void onTick(TickEvent event) {
        if(click) {
            if(objectMouseOver.entityHit != null) {
                ItemStack stack = C09Utils.getHeldItem();
                if(blockHitValue.get() && stack != null && stack.getItem() instanceof ItemSword) {
                    KeyBinding.onTick(gameSettings.keyBindUseItem.getKeyCode());
                }
                if(jitterValue.get() && MovementUtils.isMoving()) {
                    thePlayer.rotationYaw += new Random().nextBoolean() ? 1 : -1;
                    thePlayer.rotationPitch += new Random().nextBoolean() ? 1 : -1;
                    if(thePlayer.rotationPitch > 90) {
                        thePlayer.rotationPitch = 90;
                    } else if(thePlayer.rotationPitch < -90) {
                        thePlayer.rotationPitch = -90;
                    }
                }
            }
            mc.leftClickCounter = 0;
            mc.clickMouse();
            click = false;
        }
    }
}
