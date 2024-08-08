package com.github.fengye.starring.uranium.listenable.module.impl.move.noslow;

import com.github.fengye.starring.uranium.listenable.module.ModuleInMode;
import net.minecraft.item.*;

public class NoSlowMode extends ModuleInMode {
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
}
