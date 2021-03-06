package com.teddyhack.module.render;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import org.lwjgl.input.Keyboard;

public class Hud extends Module {

    public Hud() {
        super("Hud", "da gui", Keyboard.KEY_NONE, Category.Render);
    }

    @Override
    public void onEnable() { }

    @Override
    public void onDisable() { }
}
