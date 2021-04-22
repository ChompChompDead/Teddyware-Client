package com.chompchompdead.teddyware.client.module.movement;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Sprint", description = "Automatically sprints for you.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class Sprint extends Module {

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