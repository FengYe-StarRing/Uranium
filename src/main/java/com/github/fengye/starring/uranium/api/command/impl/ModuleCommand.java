package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.api.value.ArrayValue;
import com.github.fengye.starring.uranium.api.value.Numbers;
import com.github.fengye.starring.uranium.api.value.Value;
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
                sendMessage(value.getName() + ": " + value.getAsString());
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
            if(value instanceof ArrayValue<?>) {
                ArrayValue<?> arrayValue = (ArrayValue<?>) value;
                for (String s : arrayValue.getModesAsStr()) {
                    allValues = allValues.concat(s + ' ');
                }
            }
            if(value instanceof OptionValue) {
                allValues = "true false";
            }
            if(allValues.isEmpty()) {
                allValues = "All";
            }
            sendMessage("参数'" + value.getName() + "'的值为" + value.getAsString() + " 所有值: " + allValues);
            return false;
        } else if(length == 2) {
            String newValue = args[1];
            Value<?> value = getValue(name);
            if(value == null) {
                sendMessage("未找到名为'" + name + "'的参数");
                return false;
            }
            value.setAuto(newValue);
            sendMessage("已将'" + name + "'的值设置为 " + value.getAsString());
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
