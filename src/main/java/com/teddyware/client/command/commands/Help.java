package com.teddyware.client.command.commands;

import com.teddyware.api.util.ChatUtil;
import com.teddyware.client.command.Command;

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
