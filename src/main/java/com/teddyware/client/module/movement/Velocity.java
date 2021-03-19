package com.teddyware.client.module.movement;

import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventUpdate;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

    private Entity entity;

    public Entity get_entity() {
        return this.entity;
    }

    public Velocity() {
        super("Velocity", "Negates or reduces knockback.", Keyboard.KEY_NONE, Category.Movement);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {

            }
        }
    }
}
