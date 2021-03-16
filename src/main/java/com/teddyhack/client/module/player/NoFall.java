package com.teddyhack.client.module.player;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventUpdate;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "take no fall damage ez.", Keyboard.KEY_NONE, Category.Player);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            if (mc.player.fallDistance > 2) {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        }
    }

}
