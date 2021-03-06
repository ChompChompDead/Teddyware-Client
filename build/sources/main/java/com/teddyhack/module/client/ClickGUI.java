package com.teddyhack.module.client;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.ui.clickgui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "clickgui, finally", Keyboard.KEY_RSHIFT, Category.Client);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(ClickGUIScreen.INSTANCE);
    }

    @Override
    public void onDisable() { }
}
