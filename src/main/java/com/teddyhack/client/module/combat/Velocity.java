package com.teddyhack.client.module.combat;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventUpdate;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

    private Entity entity;

    public Entity get_entity() {
        return this.entity;
    }

    public Velocity() {
        super("Velocity", "anti knockback", Keyboard.KEY_NONE, Category.Combat);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {

            }
        }
    }
}
