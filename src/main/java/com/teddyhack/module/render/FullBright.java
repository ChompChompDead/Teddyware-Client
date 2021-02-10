package com.teddyhack.module.render;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "epic brightness", Keyboard.KEY_G, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1;
    }
}
