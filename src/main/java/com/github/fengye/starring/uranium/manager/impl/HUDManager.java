package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.impl.Logo;

import java.util.ArrayList;
import java.util.List;

public class HUDManager extends Manager {
    private final List<Class<? extends Element>> elementClasses = new ArrayList<>();
    private final List<Element> elements = new ArrayList<>();

    public HUDManager() {
        super("HUDManager");
    }

    @Override
    public void init() {
        super.init();
        registerElements(new Class[]{
                Logo.class
        });
        addElement(Logo.class);
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
//        for (Element element : elements) {
//            GL11.glPushMatrix();
//            GL11.glTranslated(element.getXY()[0], element.getXY()[1], 0.0);
//            element.render();
//            GL11.glPopMatrix();
//        }
    }

    public void addElement(String name) {
        for (Class<? extends Element> elementClass : elementClasses) {
            if(getElementClassName(elementClass).equals(name)) {
                try {
                    elements.add(elementClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void addElement(Class<? extends Element> cls) {
        try {
            elements.add(cls.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String getElementClassName(Class<? extends Element> cls) {
        String[] strArray = cls.getName().split("\\.");
        return strArray[strArray.length - 1];
    }

    public List<Element> getElements() {
        return elements;
    }
}
