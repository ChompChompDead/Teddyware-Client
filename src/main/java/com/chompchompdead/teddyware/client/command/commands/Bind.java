package com.chompchompdead.teddyware.client.command.commands;

import com.chompchompdead.teddyware.api.util.ChatUtil;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.command.Command;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

    public Bind() {
        super("Bind", "Changes a module bind key.", "bind [module] [key]", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];
            Teddyware.log.info(keyName);
            boolean foundModule = false;

            for (Module module : ModuleManager.modules) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    module.keyCode.setKeyCode(Keyboard.getKeyIndex(keyName.toUpperCase()));
                    ChatUtil.type(String.format("%s " + "was successfully bound to " + "%s.", module.name, Keyboard.getKeyName(module.getKey())));
                    foundModule = true;
                    break;
                }
            }
            if (!foundModule) {
                ChatUtil.type(moduleName + " was not a valid module, please try again.");
            }
        }
        if (args.length == 1) {
            String none = args[0];
            if (none.equalsIgnoreCase("none") || none.equalsIgnoreCase("clear")) {
                for (Module module : ModuleManager.modules) {
                    module.keyCode.setKeyCode(Keyboard.KEY_NONE);
                }
                ChatUtil.type("Cleared all keybinds.");
            } else {
                ChatUtil.type("The correct syntax for " + this.getName() + " is " + this.getSyntax() + ". Try again.");
            }
        }
        if (args.length == 0) {
            ChatUtil.type("The correct syntax for " + this.getName() + " is " + this.getSyntax() + ". Try again.");
        }
    }
}
