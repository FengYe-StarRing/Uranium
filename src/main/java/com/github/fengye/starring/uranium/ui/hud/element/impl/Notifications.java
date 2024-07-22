package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.manager.impl.NotificationManager;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Side;

public class Notifications extends Element {
    private final ModeValue modeValue = new ModeValue("Mode", NotificationManager.NotificationMode.values(),NotificationManager.NotificationMode.Hyaline);

    public Notifications() {
        super("Notifications", 3, 3, Side.toDefault());
    }
}
