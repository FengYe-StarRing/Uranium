package com.github.fengye.starring.uranium;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.manager.impl.EventManager;
import com.github.fengye.starring.uranium.manager.impl.LanguageManager;
import com.github.fengye.starring.uranium.manager.impl.ModuleManager;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.packet.C09Utils;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.viamcp.ViaMCP;

public class Client extends Manager {
    public static Client instance = new Client();

    public static final String RESOURCES = "Uranium";

    // Manager
    public LanguageManager languageManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;

    private String name;

    public Client() {
        super("Client");
    }

    public void init() {
        super.init();

        languageManager = new LanguageManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();

        languageManager.init();
        eventManager.init();
        moduleManager.init();

        eventManager.registerListener(new MinecraftInstance());
        eventManager.registerListener(new C09Utils());

        name = languageManager.getText("Client.Name");

        initViaMCP();
    }

    public String getName() {
        return name;
    }

    private void initViaMCP() {
        ViaMCP.create();
        ViaMCP.INSTANCE.initAsyncSlider();
        ViaMCP.INSTANCE.getAsyncVersionSlider().setVersion(ProtocolVersion.v1_8.getVersion());
    }
}
