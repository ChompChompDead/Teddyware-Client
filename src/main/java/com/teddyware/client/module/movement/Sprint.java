package com.teddyware.client.module.movement;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically run without holding your run hotkey.", Keyboard.KEY_NONE, Category.Movement);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
    }

    @Override
    public void onUpdate() {
        if (mc.player.moveForward > 0 && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }
    }
}