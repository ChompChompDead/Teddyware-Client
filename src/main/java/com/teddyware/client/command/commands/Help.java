package com.teddyware.client.command.commands;

import com.teddyware.api.util.ChatUtil;
import com.teddyware.client.Teddyware;
import com.teddyware.client.command.Command;
import com.teddyware.client.command.CommandManager;

public class Help extends Command {

    public Help() {
        super("Help", "Gives you all the commands.", "help", "cmds");
    }

    @Override
    public void onCommand(String[] args, String command) {
        for (Command command1 : Teddyware.commandManager.commands) {
            ChatUtil.type(command1.name + " | " + command1.getDescription());
        }
    }
}
