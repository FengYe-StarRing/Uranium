package com.github.fengye.starring.uranium;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.manager.impl.*;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.ProtocolUtils;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import com.github.fengye.starring.uranium.utils.misc.log.LogUtils;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import com.github.fengye.starring.uranium.utils.packet.UseUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.viamcp.ViaMCP;

import javax.swing.*;
import java.awt.*;

public class Client extends Manager {
    public static Client instance = new Client();

    public static final String T_NAME = "Client.Name";
    public static final String RESOURCES = "Uranium";
    private static final String VERSION = "1.0";
    private static final String AUTHOR = "枫叶星环";
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

        name = languageManager.getTranslate(T_NAME);
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
}
