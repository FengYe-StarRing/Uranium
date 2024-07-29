package com.github.fengye.starring.uranium.listenable.module;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.manager.impl.LanguageManager;
import com.github.fengye.starring.uranium.manager.impl.NotificationManager;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Module extends MinecraftInstance implements Listenable {
    public final String T_NAME;
    private final String name;
    private final String description;
    private final Category category;
    private final boolean canEnable;
    private final List<Value<?>> addedValues = new ArrayList<>();

    private boolean state = false;
    private int keyBind;

    public Module() {
        ModuleInfo info = JavaUtils.getClassLoader(this).getAnnotation(ModuleInfo.class);
        LanguageManager lang = Client.instance.languageManager;

        T_NAME = info.name();

        category = info.category();
        name = lang.getTranslate("L.Module." + T_NAME);
        description = lang.getTranslate("L.Module." + T_NAME + ".Description");
        keyBind = info.keyBind();
        canEnable = info.canEnable();

        addedValues.clear();
        updateAddedValues();
    }

    @Override
    public boolean handleEvents() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isCanEnable() {
        return canEnable;
    }

    public void setEnabled(boolean state) {
        this.state = state;
        if(state) {
            onEnable();
            this.state = canEnable;
        } else {
            onDisable();
        }
        NotificationManager.NotifType type = state ? NotificationManager.NotifType.Success : NotificationManager.NotifType.Error;
        NotificationManager.post(name,new String[]{"Was " + (state ? "enabled" : "disabled")}, type);
    }

    public void setEnabled() {
        setEnabled(!state);
    }

    public boolean isEnabled() {
        return state;
    }

    public String getTag() {
        return "";
    }

    public List<Value<?>> getValues() {
        List<Value<?>> values = new ArrayList<>();
        values.addAll(JavaUtils.getValues(this));
        values.addAll(addedValues);
        updateValues(values);
        return values;
    }

    public void updateValues(List<Value<?>> values) {

    }

    protected void updateValues(ModeValue modeValue, List<Value<?>> values) {
        for (Value<?> value : values) {
            String[] levels = value.getName().split("-");
            if(levels.length >= 2) {
                String level1 = levels[0];
                {
                    boolean b = true;
                    for (String s : modeValue.getModesAsStr()) {
                        if (s.equals(level1)) {
                            b = false;
                            break;
                        }
                    }
                    if(b) {
                        continue;
                    }
                }
                value.setDisplay(level1.equals(modeValue.getAsString()));
            }
        }
    }

    public void updateAddedValues() {

    }

    public void updateAddedValues(Enum<?>[] enums) {
        for (Enum<?> anEnum : enums) {
            try {
                Field mode = JavaUtils.getDeclaredField(anEnum,"MODE");
                mode.setAccessible(true);
                Object object = mode.get(anEnum);
                ModuleInMode moduleInMode = (ModuleInMode) object;
                addValues(anEnum,moduleInMode.getValues());
            } catch (IllegalAccessException ignored) {}
        }
    }

    public void addValue(Enum<?> mode,Value<?> value) {
        value.setName(mode.name().concat("-".concat(value.getName())));
        addedValues.add(value);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void addValues(Enum<?> mode,List<Value<?>> values) {
        for (Value<?> value : values) {
            addValue(mode,value);
        }
    }
}
