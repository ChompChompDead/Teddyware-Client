package com.chompchompdead.teddyware.client.command;

import com.chompchompdead.teddyware.client.command.commands.Bind;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.chompchompdead.teddyware.api.util.ChatUtil;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.command.commands.Help;
import com.chompchompdead.teddyware.client.command.commands.Prefix;
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
    public static char prefix = '!';

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

        if (!message.startsWith(String.valueOf(prefix))) return;

        event.setCanceled(true);
        message = message.substring(1);
        if (message.split(" ").length > 0) {
            boolean foundCommand = false;
            String commandName = message.split(" ")[0];
            for (Command c : commands) {
                String cmdName = c.name.toLowerCase();
                if (c.aliases.contains(commandName) || cmdName.equalsIgnoreCase(commandName)) {
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
        if (prefix == 1) {
            final char key = Keyboard.getEventCharacter();
            if (prefix == key) {
                Minecraft mc = Minecraft.getMinecraft();
                mc.displayGuiScreen(new GuiChat());
            }
        }
    }

    public static void setPrefix(char pref) {
        prefix = pref;

        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

    public static char getPrefix() {
        return prefix;
    }

}
