package com.github.fengye.starring.uranium.listenable.module;

import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.util.List;

public abstract class ModuleInMode extends MinecraftInstance {
    public List<Value<?>> getValues() {
        return JavaUtils.getValues(this);
    }
}
