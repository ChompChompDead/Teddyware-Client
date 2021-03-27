package com.teddyware.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyware.client.Teddyware;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil implements UtilInterface {
    public static void type(String text) {
        if (mc.ingameGUI != null || mc.player != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(String.format(
                    ChatFormatting.WHITE + "[" +
                            ChatFormatting.GRAY + "%s" +
                            ChatFormatting.WHITE + "]:" +
                            ChatFormatting.RESET + " %s",
                    Teddyware.instance.getName(), text
            )));
        }
    }
}
