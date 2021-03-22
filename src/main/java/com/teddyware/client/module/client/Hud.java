package com.teddyware.client.module.client;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

public class Hud extends Module {

    public Hud() {
        super("Hud", "GUI elements.", Keyboard.KEY_NONE, Category.Client);
        this.toggled = true;
    }

    @Override
    public void onEnable() { }

    @Override
    public void onDisable() { }
}
