package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.impl.KeyEvent;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.impl.move.*;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager extends Manager implements Listenable {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        super("ModuleManager");
    }

    @Override
    public void init() {
        super.init();
        modules.clear();
        Client.instance.eventManager.registerListener(this);
        // Movement
        registerModules(new Module[]{
                new Sprint(),
                new NoSlow()
        });
    }

    private void registerModule(Module module) {
        modules.add(module);
        Client.instance.eventManager.registerListener(module);
    }

    private void registerModules(Module[] modules) {
        for (Module module : modules) {
            registerModule(module);
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(Class<? extends Module> cls) {
        for (Module module : modules) {
            if(JavaUtils.getClassLoader(module) == cls) {
                return module;
            }
        }
        return null;
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle
    private void onKey(KeyEvent event) {
        int k = event.getKey();
        for (Module module : modules) {
            if(module.getKeyBind() == k) {
                module.setEnabled();
            }
        }
    }
}
