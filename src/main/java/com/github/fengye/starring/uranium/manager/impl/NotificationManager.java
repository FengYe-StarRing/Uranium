package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.game.Render2DEvent;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.Horizontal;
import com.github.fengye.starring.uranium.ui.hud.element.impl.Notifications;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.theWorld;

public class NotificationManager extends Manager implements Listenable {
    private static final List<Notif> notifs = new ArrayList<>();

    public NotificationManager() {
        super("NotificationManager");
    }

    @Override
    public void init() {
        super.init();
        Client.instance.eventManager.registerListener(this);
    }

    public static void post(String title, String[] texts, NotifType type) {
        if(theWorld == null) {
            return;
        }
        String[] texts2 = new String[texts.length];
        {
            int index = 0;
            for (String text : texts) {
                if(index >= 4) {
                    break;
                }
                texts2[index] = text;
                index++;
            }
        }
        notifs.add(new Notif(title,texts2,type));
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle(priority = Priority.HIGHEST)
    private void onRender2D(Render2DEvent event) {
        List<Element> elements = Client.instance.hudManager.getElements("Notifications");
        if(!elements.isEmpty()) {
            ((Notifications)elements.get(0)).updateNotifs();
        }
    }

    public static class Notif {
        private final String title;
        private final String[] texts;
        private final NotifType type;

        private int x;
        private int y;
        private final Timer countdownTimer = new Timer();
        public boolean countdownEnable = false;
        public boolean toBeDeleted = false;

        public Notif(String title,String[] texts,NotifType type) {
            this.title = title;
            this.texts = texts;
            this.type = type;
            List<Element> renders = Client.instance.hudManager.getElements(Notifications.class);
            if(!renders.isEmpty()) {
                x = renders.get(0).getSide().getHorizontal().equals(Horizontal.Left) ? -50 : 50;
            } else {
                x = 50;
            }
            y = 0;
        }

        public String getTitle() {
            return title;
        }

        public String[] getTexts() {
            return texts;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public NotifType getType() {
            return type;
        }

        public boolean hasTime() { // 获取的是百分比
            return countdownTimer.hasTimePassed(5 * 1000) && countdownEnable;
        }

        public void enableTimer() {
            countdownEnable = true;
            countdownTimer.reset();
        }
    }

    public static List<Notif> getNotifs() {
        return notifs;
    }

    public enum NotifType {
        Info("Info"),
        Warning("Warning"),
        Error("Error"),
        None(null),
        Success("Success");

        private final ResourceLocation icon;

        NotifType(String path) {
            if(path == null) {
                icon = null;
            } else {
                icon = MinecraftInstance.getResourceLocation("/NotifIcons/" + path + ".png");
            }
        }
        public ResourceLocation getIcon() {
            return icon;
        }
    }
}
