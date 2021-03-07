package com.teddyhack.client.module.render;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {

    private float oldgamma;

    public FullBright() {
        super("FullBright", "epic brightness", Keyboard.KEY_NONE, Category.Render);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.oldgamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.oldgamma == 100) {
            this.oldgamma = 1;
        } else {
            this.oldgamma = oldgamma;
        }
    }
}