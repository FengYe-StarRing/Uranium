package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.api.value.impl.NumberValue;
import com.github.fengye.starring.uranium.manager.impl.FontManager;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import com.github.fengye.starring.uranium.ui.hud.element.Border;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Side;

public class TargetHUD extends Element {
    private final NumberValue maxTargetsValue = new NumberValue("MaxTargets",2,2,5,1);

    private final FontRender font = FontManager.harmony18;
    private final int intervalWidth = font.getCharWidth(' ');

    public TargetHUD() {
        super("TargetHUD",9,90, Side.toDefault());
    }

    @Override
    public Border render() {
        return null;
    }
}
