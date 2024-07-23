package com.github.fengye.starring.uranium.ui.notification;

import com.github.fengye.starring.uranium.ui.hud.element.Side;
import com.github.fengye.starring.uranium.ui.notification.impl.HyalineMode;

public abstract class Notification {
    private final String title;
    private final String[] texts;
    private final NotificationType type;

    public Notification(String title, String[] texts, NotificationType type) {
        this.title = title;
        this.texts = texts;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String[] getTexts() {
        return texts;
    }

    public NotificationType getType() {
        return type;
    }

    public abstract float draw(float y,Side side);

    public enum NotificationType {
        INFO, WARNING, ERROR, NONE
    }

    public enum NotificationMode {
        Hyaline(HyalineMode.class);

        private final Class<? extends Notification> cls;

        NotificationMode(Class<? extends Notification> cls) {
            this.cls = cls;
        }

        public Class<? extends Notification> getCls() {
            return cls;
        }
    }
}
