package com.chompchompdead.teddyware.client.command.commands;

import com.chompchompdead.teddyware.client.command.Command;
import com.chompchompdead.teddyware.api.util.ChatUtil;
import com.chompchompdead.teddyware.client.Teddyware;

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
