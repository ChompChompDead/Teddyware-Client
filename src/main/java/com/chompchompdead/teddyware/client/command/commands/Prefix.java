package com.chompchompdead.teddyware.client.command.commands;

import com.chompchompdead.teddyware.client.command.Command;
import com.chompchompdead.teddyware.client.command.CommandManager;
import com.chompchompdead.teddyware.api.util.ChatUtil;

public class Prefix extends Command {

    public Prefix() {
        super("Prefix", "Changes the command prefix.", "prefix [key]", "p");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 1) {

            if (args[0].length() > 1) {
                ChatUtil.type("Your prefix must be 1 character long.");
                return;
            }

            char key = args[0].charAt(0);
            CommandManager.setPrefix(key);
            ChatUtil.type("Prefix successfully set to " + CommandManager.prefix);
        }
        if (args.length == 0) {
            ChatUtil.type("The correct syntax for " + this.getName() + " is " + this.getSyntax() + ". Try again.");
        }
    }
}
