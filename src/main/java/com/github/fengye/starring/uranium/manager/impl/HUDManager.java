package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.ui.hud.element.*;
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
                Arraylist.class,
                Notifications.class,
                TargetHUD.class
        });
        reset();
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
            float lockX = element.getX();
            float lockY = element.getY();
            GL11.glPushMatrix();
            GL11.glTranslated(lockX, lockY, 0.0);
            Border border = element.render();
            if(border != null) {
                border.setX(lockX + border.getX());
                border.setY(lockY + border.getY());
            }
            element.setBorder(border);
            GL11.glPopMatrix();
        }
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public String getElementNameByClass(Class<? extends Element> cls) {
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

    public List<Class<? extends Element>> getElementClasses() {
        return elementClasses;
    }

    public void reset() {
        elements.clear();
        addElements(new Element[]{
                new Logo(),
                new Arraylist(),
                new Notifications(),
                new TargetHUD()
        });
    }

    public List<Element> getElements(String name) {
        List<Element> elementList = new ArrayList<>();
        for (Element element : elements) {
            if(name.equals(element.T_NAME)) {
                elementList.add(element);
            }
        }
        return elementList;
    }

    public List<Element> getElements(Class<? extends Element> cls) {
        List<Element> elementList = new ArrayList<>();
        for (Element element : elements) {
            if(element.getClass() == cls) {
                elementList.add(element);
            }
        }
        return elementList;
    }
}
