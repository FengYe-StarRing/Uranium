package com.github.fengye.starring.uranium;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.manager.impl.*;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.ProtocolUtils;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import com.github.fengye.starring.uranium.utils.packet.UseUtils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.viamcp.ViaMCP;

public class Client extends Manager {
    public static Client instance = new Client();

    public static final String T_NAME = "Client.Name";
    public static final String RESOURCES = "Uranium";
    private static final String VERSION = "1.0";

    // Manager
    public LanguageManager languageManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public FileManager fileManager;
    public FontManager fontManager;

    private String name;

    public Client() {
        super("Client");
    }

    public void init() {
        super.init();

        languageManager = new LanguageManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        fileManager = new FileManager();
        fontManager = new FontManager();

        languageManager.init();
        eventManager.init();
        moduleManager.init();
        fileManager.init();
        fontManager.init();

        eventManager.registerListener(new MinecraftInstance());
        eventManager.registerListener(new C09Utils());
        eventManager.registerListener(new UseUtils());
        eventManager.registerListener(new ProtocolUtils());

        name = languageManager.getText(T_NAME);

        initViaMCP();
    }

    public static String getName() {
        return instance.name;
    }

    private void initViaMCP() {
        ViaMCP.create();
        ViaMCP.INSTANCE.initAsyncSlider();
        ViaMCP.INSTANCE.getAsyncVersionSlider().setVersion(ProtocolVersion.v1_8.getVersion());
    }

    public void stop() {
        fileManager.saveAllConfigs();
    }

    public static String getVersion() {
        return VERSION;
    }
}
