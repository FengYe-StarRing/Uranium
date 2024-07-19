package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.api.command.impl.Toggle;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.impl.ChatEvent;
import com.github.fengye.starring.uranium.manager.Manager;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends Manager implements Listenable {
    private final List<Command> commands = new ArrayList<>();
    private final String prefix = ".";

    public CommandManager() {
        super("CommandManager");
        registerCommands(new Command[]{
                new Toggle()
        });
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @EventHandle(priority = Priority.MINIMUM)
    private void onChat(ChatEvent event) {
        if(event.isCancelled() || !event.isSelf()) {
            return;
        }
        String message = event.getMessage();
    }

    private void registerCommand(Command command) {
        commands.add(command);
    }

    private void registerCommands(Command[] commands) {
        for (Command command : commands) {
            registerCommand(command);
        }
    }
}
