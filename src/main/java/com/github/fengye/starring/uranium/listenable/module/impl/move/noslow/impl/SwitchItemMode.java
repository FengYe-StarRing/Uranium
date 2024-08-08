package com.github.fengye.starring.uranium.listenable.module.impl.move.noslow.impl;

import com.github.fengye.starring.uranium.api.event.game.motion.MotionEvent;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.impl.move.noslow.NoSlowMode;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;

public class SwitchItemMode extends NoSlowMode {
    private final ModeValue switchModeValue = new ModeValue("SwitchMode",C09Utils.SwitchItemMode.values(), C09Utils.SwitchItemMode.A);
    private final OptionValue preValue = new OptionValue("Pre",false);
    private final OptionValue postValue = new OptionValue("Post",true);
    private final OptionValue antiCheckValue = new OptionValue("AntiCheck",false);

    @Override
    public void onMotion(MotionEvent event) {
        if(preValue.get() && event.isPre()) {
            switchItem();
        }
        if(postValue.get() && event.isPost()) {
            switchItem();
        }
    }

    private void switchItem() {
        C09Utils.switchItem(C09Utils.SwitchItemMode.valueOf(switchModeValue.getAsString()),antiCheckValue.get());
    }
}
