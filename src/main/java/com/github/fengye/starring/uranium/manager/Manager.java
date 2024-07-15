package com.github.fengye.starring.uranium.manager;

import com.github.fengye.starring.uranium.utils.misc.log.LogUtils;

public class Manager {
    private final String NAME;

    public Manager(String name) {
        NAME = name;
    }

    public void init() {
        LogUtils.print(NAME + " initialize");
    }
}
