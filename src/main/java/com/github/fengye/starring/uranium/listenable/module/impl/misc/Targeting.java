package com.github.fengye.starring.uranium.listenable.module.impl.misc;

import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.ModuleInfo;

@ModuleInfo(name = "Targeting",category = Category.Misc)
public class Targeting extends Module {
    public static OptionValue invisible = new OptionValue("Invisible",false);
    public static OptionValue player = new OptionValue("Player",true);
    public static OptionValue mob = new OptionValue("Mob",true);
    public static OptionValue animals = new OptionValue("Animals",true);
    public static OptionValue friend = new OptionValue("Friend",false);
    public static OptionValue teams = new OptionValue("Teams",false);
    public static OptionValue villager = new OptionValue("Villager",true);
    public static OptionValue fake = new OptionValue("Fake",false);
    public static OptionValue ironGolem = new OptionValue("IronGolem",false);
}
