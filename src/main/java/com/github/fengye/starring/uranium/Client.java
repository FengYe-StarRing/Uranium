package com.github.fengye.starring.uranium;

import com.github.fengye.starring.uranium.listenable.special.Palette;
import com.github.fengye.starring.uranium.listenable.special.Personalization;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.manager.impl.*;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.ProtocolUtils;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.misc.WebUtils;
import com.github.fengye.starring.uranium.utils.misc.log.LogUtils;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import com.github.fengye.starring.uranium.utils.packet.C0BUtils;
import com.github.fengye.starring.uranium.utils.packet.PositionUtils;
import com.github.fengye.starring.uranium.utils.packet.UseUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.viamcp.ViaMCP;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Manager {
    public static Client instance = new Client();

    public static final String T_NAME = "Client.Name";
    public static final String RESOURCES = "Uranium";
    private static final String VERSION = "1.0";
    private static final String AUTHOR = "枫叶星环";
    private static final String[] titles = new String[3];
    private static TrayIcon trayIcon;

    // Manager
    public LanguageManager languageManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public FileManager fileManager;
    public FontManager fontManager;
    public HUDManager hudManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public NotificationManager notificationManager;

    private String name;
    private boolean stop = false;
    private String hitokoto;

    public Client() {
        super("Client");
    }

    public void init() {
        super.init();
        load();
        initViaMCP();
        initTray();
    }

    public static String getName() {
        return instance.name;
    }

    private static void initViaMCP() {
        ViaMCP.create();
        ViaMCP.INSTANCE.initAsyncSlider();
        ViaMCP.INSTANCE.getAsyncVersionSlider().setVersion(ProtocolVersion.v1_8.getVersion());
    }

    public void stop() {
        stop = true;
        LogUtils.print("Stopping " + name + " Client...");
        fileManager.saveAllConfigs();
    }

    public static String getVersion() {
        return VERSION;
    }

    public boolean isStop() {
        return stop;
    }

    public void load() {
        languageManager = new LanguageManager();
        eventManager = new EventManager();
        fontManager = new FontManager();
        moduleManager = new ModuleManager();
        hudManager = new HUDManager();
        fileManager = new FileManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        notificationManager = new NotificationManager();

        languageManager.init();
        eventManager.init();
        moduleManager.init();
        hudManager.init();
        fileManager.init();
        fontManager.init();
        commandManager.init();
        configManager.init();
        notificationManager.init();

        eventManager.registerListener(new MinecraftInstance());
        eventManager.registerListener(new C09Utils());
        eventManager.registerListener(new UseUtils());
        eventManager.registerListener(new ProtocolUtils());
        eventManager.registerListener(new PositionUtils());
        eventManager.registerListener(new C0BUtils());

        eventManager.registerListener(new Palette());
        eventManager.registerListener(new Personalization());

        name = languageManager.getTranslate(T_NAME);
        setTitle(name + ' ' + VERSION,0);
        setTitle(hitokoto = getRandomHitokoto(),1);
    }

    public static void initTray() {
        trayIcon = new TrayIcon(new ImageIcon(JavaUtils.getResource(Client.RESOURCES + "/Icons/Icon_32x32.png")).getImage());
        if(SystemTray.isSupported()) {
            trayIcon.setImageAutoSize(true);
        }
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException ignored) {}
    }

    public static void displayTray(String Title, String Text, TrayIcon.MessageType type) {
        trayIcon.displayMessage(Title, Text, type);
    }

    public static void displayTray(String text,TrayIcon.MessageType type) {
        displayTray(instance.name + ' ' + VERSION, text, type);
    }

    public static void displayTray(String text) {
        displayTray(text, TrayIcon.MessageType.INFO);
    }

    public static String getText(String pack) {
        return instance.languageManager.getTranslate("Text." + pack);
    }

    public static String getAuthor() {
        return AUTHOR;
    }

    public static String getTitle() {
        String title = "";
        List<String> bufferList = new ArrayList<>();
        for (String s : titles) {
            if(s == null || s.isEmpty()) {
                continue;
            }
            bufferList.add(s);
        }
        for(int i = 0;i < bufferList.size();i++) {
            String text = bufferList.get(i);
            title = title.concat(text + (i == bufferList.size() - 1 ? "" : " | "));
        }
        return title;
    }

    public static void setTitle(String text,int index) {
        titles[index] = text;
        Display.setTitle(getTitle());
    }

    public static String getRandomHitokoto() {
        try {
            return WebUtils.get("https://v1.hitokoto.cn/?&encode=text");
        } catch (IOException e) {
            return null;
        }
    }

    public String getHitokoto() {
        return hitokoto;
    }
}
