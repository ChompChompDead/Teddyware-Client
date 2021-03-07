package com.teddyhack.client.module.movement;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.listeners.EventUpdate;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class Step extends Module {

    public Step() {
        super("Step", "go up block without jump ez.", Keyboard.KEY_NONE, Category.Movement);
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
