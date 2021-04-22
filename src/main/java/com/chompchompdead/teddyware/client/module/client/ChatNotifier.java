package com.chompchompdead.teddyware.client.module.client;

import com.chompchompdead.teddyware.api.util.ChatUtil;
import com.chompchompdead.teddyware.api.event.events.EventNotifier;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "ChatNotifier", description = "Notifies you in chat when a module is toggled.", key = Keyboard.KEY_NONE, category = Category.Client)
public class ChatNotifier extends Module {

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