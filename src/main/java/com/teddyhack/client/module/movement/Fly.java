package com.teddyhack.client.module.movement;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {

    public Fly() {
        super("Fly", "Fly lolololo", Keyboard.KEY_NONE, Category.Movement);
    }

    @Override
    public void onEnable() {
        mc.player.jump();
        mc.player.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        mc.player.capabilities.isFlying = false;
    }

}