package com.teddyware.client.module.movement;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Fly", description = "Fly as if you are in creative.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class Fly extends Module {

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.jump();
            mc.player.capabilities.isFlying = true;
        }
    }

    @Override
    public void onDisable() {
        mc.player.capabilities.isFlying = false;
    }

}