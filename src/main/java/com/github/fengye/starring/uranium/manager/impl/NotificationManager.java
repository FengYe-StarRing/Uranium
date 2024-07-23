package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.hud.element.Side;
import com.github.fengye.starring.uranium.ui.hud.element.Vertical;
import com.github.fengye.starring.uranium.ui.hud.element.impl.Notifications;
import com.github.fengye.starring.uranium.ui.notification.Notification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.theWorld;

public class NotificationManager extends Manager {
    private static final List<Notification> notifications = new ArrayList<>();
    private static final int interval = 8;

    public NotificationManager() {
        super("NotificationManager");
        notifications.clear();
    }

    public static void post(String title, String[] texts,Notification.NotificationType type,Class<? extends Notification> cls) {
        if(theWorld == null) {
            return;
        }
        try {
            Constructor<? extends Notification> constructor = cls.getConstructor(String.class, String[].class, Notification.NotificationType.class);
            notifications.add(constructor.newInstance(title,texts,type));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {}
    }

    public static void post(String title, String[] texts,Notification.NotificationType type) {
        post(title,texts,type,getCurrentMode());
    }

    public static Class<? extends Notification> getCurrentMode() {
        return Notification.NotificationMode.valueOf(Notifications.modeValue.getAsString()).getCls();
    }

    public static void draw(Side side) {
        float y = 0;
        Vertical vertical = side.getVertical();
        for (Notification notification : notifications) {
            float height = notification.draw(y,side) + interval;
            y += vertical == Vertical.Up ? height : -height;
        }
    }
}
