package com.github.fengye.starring.uranium.listenable.module.impl.move.noslow;

import com.github.fengye.starring.uranium.api.event.impl.motion.MotionEvent;
import net.minecraft.item.*;

public class NoSlowMode {
    public static boolean isHolding(ItemStack stack,boolean sword,boolean con,boolean bow) {
        if(stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if(item instanceof ItemSword && sword) {
            return true;
        }
        if((item instanceof ItemFood || item instanceof ItemPotion || item instanceof ItemBucketMilk) && con) {
            return true;
        }
        return item instanceof ItemBow && bow;
    }

    public void onMotion(MotionEvent event) {

    }
}
