package com.github.fengye.starring.uranium.ui.hud.element.impl;

import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.manager.impl.NotificationManager;
import com.github.fengye.starring.uranium.ui.hud.element.*;
import com.github.fengye.starring.uranium.ui.notification.Notification;

public class Notifications extends Element {
    public static final ModeValue modeValue = new ModeValue("Mode", Notification.NotificationMode.values(),Notification.NotificationMode.Hyaline);

    public Notifications() {
        super("Notifications", 3, 3, new Side(Horizontal.Right, Vertical.Down));
    }

    @Override
    public Border render() {
        NotificationManager.draw(getSide());
        return null;
    }
}
