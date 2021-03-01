package com.teddyhack.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyhack.Client;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil implements Util {

    public static void type(final String text) {
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(String.format(
                ChatFormatting.WHITE + "["
                + ChatFormatting.AQUA + "%s"
                + ChatFormatting.WHITE + "]:"
                + ChatFormatting.WHITE + " %s",
                Client.instance.getName(), text
        )));
    }

}
