package com.github.fengye.starring.uranium;

import com.github.fengye.starring.uranium.listenable.module.impl.move.Sprint;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.manager.impl.EventManager;
import com.github.fengye.starring.uranium.manager.impl.LanguageManager;
import com.github.fengye.starring.uranium.manager.impl.ModuleManager;

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

        name = languageManager.getText("Client.Name");
    }

    public String getName() {
        return name;
    }
}
