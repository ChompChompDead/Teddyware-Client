package com.teddyware.client.module.client;

import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventNotifier;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.api.util.ChatUtil;
import org.lwjgl.input.Keyboard;

public class ChatNotifier extends Module {

    public ChatNotifier() {
        super("ChatNotifier", "notifies you when a module is toggled or other stuff.", Keyboard.KEY_NONE, Category.Client);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventNotifier) {
            String module = ((EventNotifier) e).name;
            boolean moduleToggled = ((EventNotifier) e).toggle;
            ChatUtil.type(module + " is now " + Module.getToggledStatus(moduleToggled));
        }
    }
}