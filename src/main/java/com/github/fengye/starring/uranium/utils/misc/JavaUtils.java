package com.github.fengye.starring.uranium.utils.misc;

import com.github.fengye.starring.uranium.api.file.config.Config;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.ui.font.FontRender;
import net.minecraft.client.gui.GuiTextField;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaUtils {
    public static Class<?> getClassLoader() {
        return Class.class;
    }

    public static Class<?> getClassLoader(Object object) {
        return object.getClass();
    }

    public static InputStream getStream(String path) {
        return getClassLoader().getResourceAsStream("/assets/minecraft/" + path);
    }

    public static List<Method> getDeclaredMethods(Class<?> cls) {
        return Arrays.asList(cls.getDeclaredMethods());
    }

    public static List<Field> getDeclaredFields(Class<?> cls) {
        return Arrays.asList(cls.getDeclaredFields());
    }

    public static List<Field> getDeclaredFields(Object object) {
        return getDeclaredFields(getClassLoader(object));
    }

    public static List<Field> getFields(Object obj,Class<?> cls) {
        List<Field> fields = new ArrayList<>();
        for (Field field : getDeclaredFields(obj)) {
            if(field.getType() == cls) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        return fields;
    }

    public static List<Value<?>> getValues(Object obj) {
        List<Value<?>> list = new ArrayList<>();
        for (Field field : getDeclaredFields(obj.getClass())) {
            field.setAccessible(true);
            try {
                Object object = field.get(obj);
                if(object instanceof Value<?>) {
                    list.add((Value<?>) object);
                }
            } catch (IllegalAccessException ignored) {}
        }
        return list;
    }

    public static List<FontRender> getFonts(Object obj) {
        List<FontRender> list = new ArrayList<>();
        for (Field field : getDeclaredFields(obj.getClass())) {
            field.setAccessible(true);
            try {
                Object object = field.get(obj);
                if(object instanceof FontRender) {
                    list.add((FontRender) object);
                }
            } catch (IllegalAccessException ignored) {}
        }
        return list;
    }

    public static List<Config> getConfigs(Object obj) {
        List<Config> list = new ArrayList<>();
        for (Field field : getDeclaredFields(obj.getClass())) {
            field.setAccessible(true);
            try {
                Object object = field.get(obj);
                if(object instanceof Config) {
                    list.add((Config) object);
                }
            } catch (IllegalAccessException ignored) {}
        }
        return list;
    }

    public static java.net.URL getResource(String path) {
        return getClassLoader().getResource("/assets/minecraft/" + path);
    }
    public static List<Field> getFields(Object obj) {
        return getFields(obj,obj.getClass());
    }

    public static Class<?>[] getDeclaredClasses(Object obj) {
        return getDeclaredClasses(obj.getClass());
    }

    public static Class<?>[] getDeclaredClasses(Class<?> cls) {
        return cls.getDeclaredClasses();
    }

    public static Field getDeclaredField(Class<?> cls,String name) {
        try {
            return cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static Field getDeclaredField(Object obj,String name) {
        return getDeclaredField(obj.getClass(),name);
    }

    public static List<GuiTextField> getGuiTextFields(Object obj) {
        List<GuiTextField> list = new ArrayList<>();
        for (Field field : getDeclaredFields(obj.getClass())) {
            field.setAccessible(true);
            try {
                Object object = field.get(obj);
                if(object instanceof GuiTextField) {
                    list.add((GuiTextField) object);
                }
            } catch (IllegalAccessException ignored) {}
        }
        return list;
    }
}
