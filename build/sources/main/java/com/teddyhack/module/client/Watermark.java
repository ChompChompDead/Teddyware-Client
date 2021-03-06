package com.teddyhack.module.client;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import org.lwjgl.input.Keyboard;

public class Watermark extends Module {

    public Watermark() {
        super("Watermark", "Shows the client name and version.", Keyboard.KEY_NONE, Category.Client);
    }

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }
}
