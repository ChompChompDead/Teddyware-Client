package com.teddyware.client.module.player;

import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventPacket;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "no kb. ez.", Keyboard.KEY_NONE, Category.Player);
    }

    public void onEnable() { }

    public void onDisable() { }

    public void onEvent(Event e) {
        if (e instanceof EventPacket.Receive) {
            EventPacket.Receive event = (EventPacket.Receive) e;

            if (event.getPacket() instanceof SPacketExplosion) {
                event.setCancelled(true);
            }

            if (event.getPacket() instanceof SPacketEntityVelocity) {
                if (((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                    event.setCancelled(true);
                }
            }
        }
    }
}