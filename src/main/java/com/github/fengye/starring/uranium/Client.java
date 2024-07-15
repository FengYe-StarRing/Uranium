package com.github.fengye.starring.uranium;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.manager.impl.EventManager;
import com.github.fengye.starring.uranium.manager.impl.LanguageManager;

public class Client extends Manager {
    public static Client instance = new Client();

    public static final String RESOURCES = "Uranium";

    // Manager
    public LanguageManager languageManager;
    public EventManager eventManager;

    private String name;

    public Client() {
        super("Client");
    }

    public void init() {
        super.init();

        languageManager = new LanguageManager();
        eventManager = new EventManager();

        languageManager.init();
        eventManager.init();

        name = languageManager.getText("Client.Name");
    }

    public String getName() {
        return name;
    }
}
