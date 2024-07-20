package com.github.fengye.starring.uranium.api.command.impl;

import com.github.fengye.starring.uranium.api.command.Command;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Say extends Command {
    public Say() {
        super("Say",new String[]{"Say"},"say <message>");
    }

    @Override
    public boolean execute(String[] args) {
        if(args.length != 0) {
            String message = "";
            for (String arg : args) {
                message = message.concat(arg + ' ');
            }
            sendPacket(new C01PacketChatMessage(message));
            return false;
        } else {
            return true;
        }
    }
}
