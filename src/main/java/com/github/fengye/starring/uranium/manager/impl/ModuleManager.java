package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.game.KeyEvent;
import com.github.fengye.starring.uranium.listenable.module.Category;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.listenable.module.impl.combat.*;
import com.github.fengye.starring.uranium.listenable.module.impl.misc.*;
import com.github.fengye.starring.uranium.listenable.module.impl.move.*;
import com.github.fengye.starring.uranium.listenable.module.impl.render.*;
import com.github.fengye.starring.uranium.listenable.module.impl.world.*;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;
import org.lwjgl.input.Keyboard;

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
        // Combat
        registerModules(new Module[]{
                new KillAura(),
                new Criticals(),
                new AutoClicker(),
                new AntiKnockback(),
                new SuperKnockback()
        });
        // Render
        registerModules(new Module[]{
                new ClickGui(),
                new HUD(),
                new HUDEditGui(),
                new FullBright(),
                new FovWiden(),
                new Animations()
        });
        // Movement
        registerModules(new Module[]{
                new Sprint(),
                new NoSlow(),
                new Speed(),
                new GuiMove()
        });
        // World
        registerModules(new Module[]{
                new Timer(),
                new WorldState(),
                new Disabler(),
                new AutoDisable()
        });
        // Misc
        registerModules(new Module[]{
                new Protocol(),
                new Targeting(),
                new DebugTool()
        });
        modules.sort((mod, mod1) -> {
            int char0 = mod.getName().charAt(0);
            int char1 = mod1.getName().charAt(0);
            return -Integer.compare(char1, char0);
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

    public List<Module> getModulesInType(Category category) {
        List<Module> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if(category.equals(module.getCategory())) {
                modules.add(module);
            }
        }
        return modules;
    }

    public List<Module> getModulesHaveKey() {
        List<Module> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if(module.getKeyBind() != Keyboard.KEY_NONE) {
                modules.add(module);
            }
        }
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if(module.getName().equals(name) || module.T_NAME.equals(name)) {
                return module;
            }
        }
        return null;
    }
}
