package com.chompchompdead.teddyware.client.module.movement;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "ReverseStep", description = "Step, but it activates the reverse way.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class ReverseStep extends Module {

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.5F;
    }

    @Override
    public void onUpdate() {
        if (mc.player.onGround) {
            mc.player.motionY -= 1.0;
        }
    }

}
