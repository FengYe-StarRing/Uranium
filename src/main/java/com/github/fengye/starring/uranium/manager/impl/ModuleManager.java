package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.impl.move.Sprint;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager extends Manager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        super("ModuleManager");
    }

    @Override
    public void init() {
        super.init();
        modules.clear();
        // Movement
        registerModules(new Module[]{
                new Sprint()
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
}
