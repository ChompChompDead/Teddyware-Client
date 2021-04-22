package com.chompchompdead.teddyware.client.module.player;

import com.chompchompdead.teddyware.api.event.events.EventPacket;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Velocity", description = "Take no knockback.", key = Keyboard.KEY_NONE, category = Category.Player)
public class Velocity extends Module {

    public void onEnable() {
        Teddyware.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        Teddyware.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private final Listener<EventPacket.Receive> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketExplosion) {
            event.cancel();
        }

        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if (((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                event.cancel();
            }
        }
    });
}