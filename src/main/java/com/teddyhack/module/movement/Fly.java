package com.teddyhack.module.movement;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventUpdate;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {

    public Fly() {
        super("Fly", "Fly lolololo", Keyboard.KEY_R, Category.MOVEMENT);
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

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventUpdate) {
            if(e.isPre()) {

            }
        }
    }
}
