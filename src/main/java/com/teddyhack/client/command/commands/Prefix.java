package com.teddyhack.client.command.commands;

import com.teddyhack.api.util.ChatUtil;
import com.teddyhack.client.command.Command;
import com.teddyhack.client.command.CommandManager;

public class Prefix extends Command {

    public Prefix() {
        super("prefix", "changes command prefix", "prefix [key]", "p");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 1) {
            String key = args[0];
            CommandManager.setPrefix(key);
            ChatUtil.type("Prefix successfully set to " + CommandManager.prefix);
        }
        if (args.length == 0) {
            ChatUtil.type("Wrong syntax. Correct use for this command is " + syntax + ".");
        }
    }
}
