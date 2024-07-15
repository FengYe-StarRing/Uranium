package com.github.fengye.starring.uranium.api.event;

public @interface EventHandle {
    byte priority() default Priority.NORMAL;
}
