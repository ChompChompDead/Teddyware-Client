package com.teddyhack.module.client;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventNotifier;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.util.ChatUtil;
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