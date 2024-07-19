package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.api.command.Command;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", new String[]{"Toggle","Enable","t"}, "t <module name>");
    }
}
