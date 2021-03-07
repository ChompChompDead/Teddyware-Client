package com.teddyhack.client.module.client;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import org.lwjgl.input.Keyboard;

public class Coords extends Module {

    public Coords() {
        super("Coords", "Shows coordartinitesndak", Keyboard.KEY_NONE, Category.Client);
    }

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }
}
