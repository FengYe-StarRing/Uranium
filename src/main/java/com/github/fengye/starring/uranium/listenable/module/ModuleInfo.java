package com.github.fengye.starring.uranium.listenable.module;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();
    Category category();
    int keyBind() default Keyboard.KEY_NONE;
    boolean canEnable() default true;
}
