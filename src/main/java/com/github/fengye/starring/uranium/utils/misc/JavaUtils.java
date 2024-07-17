package com.github.fengye.starring.uranium.utils.misc;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.value.Value;
import net.minecraft.util.ResourceLocation;

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

    public static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(Client.RESOURCES + path);
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
}
