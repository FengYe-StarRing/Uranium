package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.Client;
import com.github.fengye.starring.uranium.api.command.Command;
import com.github.fengye.starring.uranium.api.command.impl.ModuleCommand;
import com.github.fengye.starring.uranium.api.command.impl.Say;
import com.github.fengye.starring.uranium.api.command.impl.Toggle;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.Priority;
import com.github.fengye.starring.uranium.api.event.impl.ChatEvent;
import com.github.fengye.starring.uranium.listenable.module.Module;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.MinecraftInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager extends Manager implements Listenable {
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        super("CommandManager");
    }

    @Override
    public void init() {
        super.init();
        commands.clear();
        registerCommands(new Command[]{
                new Toggle(),
                new Say()
        });
        for (Module module : Client.instance.moduleManager.getModules()) {
            String[] alias;
            if(module.getName().equals(module.T_NAME)) {
                alias = new String[]{module.T_NAME};
            } else {
                alias = new String[]{module.T_NAME,module.getName()};
            }
            registerCommand(new ModuleCommand(alias,module));
        }
        Client.instance.eventManager.registerListener(this);
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
        if(message.charAt(0) == '.') {
            String[] buffer = message.substring(1).split(" ");
            List<String> args = new ArrayList<>(Arrays.asList(buffer));
            args.remove(0);
            for (Command command : commands) {
                for (String alias : command.getAlias()) {
                    if(alias.equalsIgnoreCase(buffer[0])) {
                        if(command.execute(args.toArray(new String[0]))) {
                            MinecraftInstance.sendMessage('.' + command.getSyntax());
                        }
                    }
                }
            }
            event.cancelEvent();
        }
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
