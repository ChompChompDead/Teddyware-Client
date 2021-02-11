package com.teddyhack.module.movement;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventUpdate;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class Step extends Module {

    public Step() {
        super("Step", "go up block without jump ez.", Keyboard.KEY_T, Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if (toggled) {
                    if((mc.player.collidedHorizontally) && (mc.player.onGround)) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.42, mc.player.posZ, mc.player.onGround));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.75, mc.player.posZ, mc.player.onGround));
                        mc.player.stepHeight = 1.0f;
                    }
                } else {
                    mc.player.stepHeight = 0.5f;
                }
            }
        }
    }
}
