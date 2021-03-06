package com.teddyhack.module.client;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
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
