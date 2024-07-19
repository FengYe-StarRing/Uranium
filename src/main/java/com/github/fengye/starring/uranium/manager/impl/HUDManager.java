package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.hud.element.Element;
import com.github.fengye.starring.uranium.ui.hud.element.impl.*;
import org.lwjgl.opengl.GL11;

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
                Logo.class,
                Arraylist.class
        });
        addElements(new Element[]{
                new Logo(),
                new Arraylist()
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
            GL11.glTranslated(element.getX(), element.getY(), 0.0);
            element.render();
            GL11.glPopMatrix();
        }
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    private String getElementNameByClass(Class<? extends Element> cls) {
        String[] strArray = cls.getName().split("\\.");
        return strArray[strArray.length - 1];
    }

    public List<Element> getElements() {
        return elements;
    }

    public void addElements(Element[] elements) {
        for (Element element : elements) {
            addElement(element);
        }
    }

    public Element newElement(String name) {
        try {
            Class<? extends Element> cls = getElementClassByName(name);
            if(cls == null) {
                return null;
            }
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    private Class<? extends Element> getElementClassByName(String name) {
        for (Class<? extends Element> elementClass : elementClasses) {
            if(getElementNameByClass(elementClass).equals(name)) {
                return elementClass;
            }
        }
        return null;
    }
}
