package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.api.value.Numbers;
import com.github.fengye.starring.uranium.api.value.Value;
import com.github.fengye.starring.uranium.api.value.impl.ModeValue;
import com.github.fengye.starring.uranium.api.value.impl.OptionValue;
import com.github.fengye.starring.uranium.listenable.module.Module;

import java.util.List;

public class ModuleCommand extends Command {
    private final Module module;

    public ModuleCommand(String[] alias,Module module) {
        super(null, alias, "module <value name> <new value>");
        this.module = module;
    }

    private List<Value<?>> getValues() {
        return module.getValues();
    }

    @Override
    public boolean execute(String[] args) {
        int length = args.length;
        String name = null;
        if(length >= 1) {
            name = args[0];
        }
        if(length == 0) {
            List<Value<?>> values = getValues();
            if(values.isEmpty()) {
                sendMessage("此模块没有可供调的参数");
                return false;
            }
            sendMessage("参数列表:");
            for (Value<?> value : values) {
                sendMessage(value.getName() + ": " + value.get());
            }
            return false;
        } else if(length == 1) {
            Value<?> value = getValue(name);
            if(value == null) {
                sendMessage("未找到名为'" + name + "'的参数");
                return false;
            }
            String allValues = "";
            if(value instanceof Numbers) {
                Numbers<?> numbers = (Numbers<?>) value;
                allValues = allValues.concat(numbers.getMin() + " ~ " + numbers.getMax());
            }
            if(value instanceof ModeValue) {
                ModeValue modeValue = (ModeValue) value;
                for (Enum<?> mode : modeValue.getMODES()) {
                    allValues = allValues.concat(mode.name() + ' ');
                }
            }
            if(value instanceof OptionValue) {
                allValues = "true false";
            }
            sendMessage("参数'" + value.getName() + "'的值为" + value.get() + " 所有值: " + allValues);
            return false;
        } else if(length == 2) {
            String newValue = args[1];
            Value value = getValue(name);
            if(value == null) {
                sendMessage("未找到名为'" + name + "'的参数");
                return false;
            }
            if(value instanceof Numbers) {
                value.set(Double.valueOf(newValue));
            }
            if(value instanceof ModeValue) {
                ((ModeValue)value).set(newValue);
            }
            if(value instanceof OptionValue) {
                value.set(Boolean.valueOf(newValue));
            }
            sendMessage("已将'" + name + "'的值设置为 " + value.get());
            return false;
        }
        return true;
    }

    private Value<?> getValue(String name) {
        for (Value<?> value : getValues()) {
            if(value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
