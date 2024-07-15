package com.github.fengye.starring.uranium.utils.misc;

import java.io.InputStream;

public class JavaUtils {
    public static Class<?> getClassLoader() {
        return Class.class;
    }

    public static InputStream getStream(String path) {
        return getClassLoader().getResourceAsStream("/assets/minecraft/" + path);
    }
}
