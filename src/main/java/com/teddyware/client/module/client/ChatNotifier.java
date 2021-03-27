package com.teddyware.client.module.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyware.api.event.events.EventNotifier;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.api.util.ChatUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.lwjgl.input.Keyboard;

public class ChatNotifier extends Module {

    public ChatNotifier() {
        super("ChatNotifier", "notifies you when a module is toggled or other stuff.", Keyboard.KEY_NONE, Category.Client);
    }

    @EventHandler
    private final Listener<EventNotifier.EventNotifierEnable> eventNotifierEnableListener = new Listener<>(event -> {
        Module module = event.module;
        ChatUtil.type(module.name + " is now " + module.getToggledStatus(module.toggled));
    });

    @EventHandler
    private final Listener<EventNotifier.EventNotifierDisable> eventNotifierDisableListener = new Listener<>(event -> {
        Module module = event.module;
        ChatUtil.type(module.name + " is now " + module.getToggledStatus(module.toggled));
    });
}