package com.teddyhack.module.movement;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventUpdate;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically run without holding your run hotkey.", Keyboard.KEY_C, Category.MOVEMENT);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventUpdate) {
            if(e.isPre()) {
                if (mc.player.moveForward > 0 && !mc.player.isSneaking())
                    mc.player.setSprinting(true);
            }
        }
    }
}
