package com.teddyhack.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyhack.api.util.ChatUtil;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.command.Command;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

    public Bind() {
        super("bind", "bind a key :D", "bind [module] [key]", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];
            Teddyhack.log.info(keyName);
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
                ChatUtil.type("Wrong syntax. The correct usage for this command is " + ChatFormatting.ITALIC + syntax + ChatFormatting.RESET + ".");
            }
        }
        if (args.length == 0) {
            ChatUtil.type("Wrong syntax. The correct usage for this command is " + ChatFormatting.ITALIC + syntax + ChatFormatting.RESET + ".");
        }
    }
}
