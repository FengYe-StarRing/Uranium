package com.github.fengye.starring.uranium.utils.misc;

import com.github.fengye.starring.uranium.Client;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
}
