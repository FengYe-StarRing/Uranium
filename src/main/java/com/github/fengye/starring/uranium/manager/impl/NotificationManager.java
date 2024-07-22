package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.notification.Notification;
import com.github.fengye.starring.uranium.ui.notification.impl.HyalineMode;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager extends Manager {
    private final List<Notification> notifications = new ArrayList<>();

    public NotificationManager() {
        super("NotificationManager");
    }

    public enum NotificationType {
        INFO, WARNING, ERROR
    }

    public enum NotificationMode {
        Hyaline(new HyalineMode());

        private final Notification MODE;

        NotificationMode(Notification mode) {
            MODE = mode;
        }

        public Notification getMode() {
            return MODE;
        }
    }
}
