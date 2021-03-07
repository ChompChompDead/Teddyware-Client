package com.teddyhack.client.module.client;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import org.lwjgl.input.Keyboard;

public class ArrayList extends Module {

    public ArrayList() {
        super("ArrayList", "Shows which modules are toggled", Keyboard.KEY_NONE, Category.Client);
    }

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }
}
