package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.hud.Element;
import com.github.fengye.starring.uranium.ui.hud.impl.Logo;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class HudManager extends Manager {
    private final List<Class<?>> elementClasses = new ArrayList<>();
    private final List<Element> elements = new ArrayList<>();

    public HudManager() {
        super("HudManager");
    }

    @Override
    public void init() {
        super.init();
        registerElements(new Class[]{
                Logo.class
        });
    }

    private void registerElement(Class<? extends Element> cls) {
        elementClasses.add(cls);
    }

    private void registerElements(Class<? extends Element>[] classes) {
        for (Class<? extends Element> aClass : classes) {
            registerElement(aClass);
        }
    }

    public void render() {
        for (Element element : elements) {
            GL11.glPushMatrix();
//            GL11.glTranslated(element.getXY()[0], element.getXY()[1], 0.0);
            element.render();
//            GL11.glPopMatrix();
        }
    }
}
