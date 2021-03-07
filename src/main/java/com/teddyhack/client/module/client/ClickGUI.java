package com.teddyhack.client.module.client;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.listeners.EventUpdate;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.ui.clickgui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "clickgui, finally", Keyboard.KEY_RSHIFT, Category.Client);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(ClickGUIScreen.INSTANCE);
        if (mc.currentScreen == ClickGUIScreen.INSTANCE) {
            this.disable();
        }
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if (mc.currentScreen != ClickGUIScreen.INSTANCE) {
                    mc.displayGuiScreen(ClickGUIScreen.INSTANCE);
                } else {
                    this.disable();
                }
            }
        }
    }
}
