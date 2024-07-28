package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.timer.Timer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static com.github.fengye.starring.uranium.utils.MinecraftInstance.theWorld;

public class NotificationManager extends Manager {
    private static final List<Notif> notifs = new ArrayList<>();

    public NotificationManager() {
        super("NotificationManager");
    }

    public static void post(String title,String[] texts,NotifType type) {
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
            x = 50;
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
        Info("Info"),Warning("Warning"),Error("Error"),None(null);

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
