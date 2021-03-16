package com.teddyhack.client.command.commands;

import com.teddyhack.api.util.ChatUtil;
import com.teddyhack.client.command.Command;
import com.teddyhack.client.command.CommandManager;

public class Help extends Command {

    public Help() {
        super("help", "give you a list of commands", "help", "cmds");
    }

    @Override
    public void onCommand(String[] args, String command) {
        ChatUtil.type("Prefix | Changes the command prefix");
        ChatUtil.type("Bind | Bind a key to a module (do " + prefix + "bind none to clear all binds)");
    }
}
