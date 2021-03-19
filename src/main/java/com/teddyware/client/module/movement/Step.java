package com.teddyware.client.module.movement;

import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventUpdate;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class Step extends Module {

    public Step() {
        super("Step", "Allows you to step up normal blocks like stairs.", Keyboard.KEY_NONE, Category.Movement);
    }

    @Override
    public void onDisable() { mc.player.stepHeight = 0.5F; }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if (toggled) {
                    if ((mc.player.collidedHorizontally) && (!mc.player.isSneaking()) && (mc.player.onGround)) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.42, mc.player.posZ, mc.player.onGround));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.75, mc.player.posZ, mc.player.onGround));
                        mc.player.stepHeight = 1.0f;
                    }
                }
            }
        }
    }
}
