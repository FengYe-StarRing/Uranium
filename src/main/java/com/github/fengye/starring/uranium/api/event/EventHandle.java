package com.github.fengye.starring.uranium.api.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandle {
    byte priority() default Priority.NORMAL;
}
