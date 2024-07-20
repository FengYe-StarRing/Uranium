package com.github.fengye.starring.uranium.ui.font;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FontInfo {
    String name();
    int size();
}
