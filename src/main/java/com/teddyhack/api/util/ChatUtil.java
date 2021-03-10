package com.teddyhack.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyhack.client.Teddyhack;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil implements Util {

    public static void type(final String text) {
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(String.format(
                ChatFormatting.WHITE + "[" +
                ChatFormatting.GRAY + "%s" +
                ChatFormatting.WHITE + "]:" +
                ChatFormatting.WHITE + " %s",
                Teddyhack.instance.getName(), text
        )));
    }

}
