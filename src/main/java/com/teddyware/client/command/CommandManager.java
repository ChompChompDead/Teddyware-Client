package com.teddyware.client.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyware.api.util.ChatUtil;
import com.teddyware.client.Teddyware;
import com.teddyware.client.command.commands.Bind;
import com.teddyware.client.command.commands.Help;
import com.teddyware.client.command.commands.Prefix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public List<Command> commands = new ArrayList<Command>();
    public static String prefix = "!";

    public CommandManager() {
        MinecraftForge.EVENT_BUS.register(this);
        registerCommands();
    }

    public void registerCommands() {
        commands.add(new Help());
        commands.add(new Prefix());
        commands.add(new Bind());
    }

    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        String message = event.getMessage();

        if (!message.startsWith(prefix)) return;

        event.setCanceled(true);
        message = message.substring(prefix.length());
        if (message.split(" ").length > 0) {
            boolean foundCommand = false;
            String commandName = message.split(" ")[0];
            for (Command c : commands) {
                if (c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    foundCommand = true;
                    break;
                }
            }
            if (!foundCommand) {
                ChatUtil.type(
                "This command does not exist. Please use " +
                     ChatFormatting.ITALIC + prefix + "help " +
                     ChatFormatting.RESET + "for the command list."
                );
            }
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if (prefix.length() == 1) {
            final char key = Keyboard.getEventCharacter();
            if (prefix.charAt(0) == key) {
                Minecraft mc = Minecraft.getMinecraft();
                mc.displayGuiScreen(new GuiChat());
            }
        }
    }

    public static void setPrefix(String pref) {
        prefix = pref;

        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

    public static String getPrefix() {
        return prefix;
    }

}
