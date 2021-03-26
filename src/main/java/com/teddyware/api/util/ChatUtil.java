package com.teddyware.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyware.client.Teddyware;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil implements UtilInterface {

    public static String message;

    public static void type(final String text) {
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(String.format(
                ChatFormatting.WHITE + "[" +
                ChatFormatting.GRAY + "%s" +
                ChatFormatting.WHITE + "]:" +
                ChatFormatting.WHITE + " %s",
                Teddyware.instance.getName(), text
        )));
    }

    public static String getMessage() {
        return message;
    }

}
