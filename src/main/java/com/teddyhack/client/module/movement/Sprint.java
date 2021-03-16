package com.teddyhack.client.module.movement;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventUpdate;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
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
    public void onEvent(Event e) {
        if(e instanceof EventUpdate) {
            if(e.isPre()) {
                if (mc.player.moveForward > 0 && !mc.player.isSneaking()) {
                    mc.player.setSprinting(true);
                }
            }
        }
    }
}