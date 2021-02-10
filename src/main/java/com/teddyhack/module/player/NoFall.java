package com.teddyhack.module.player;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventUpdate;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "take no fall damage ez.", (Keyboard.KEY_V), Category.PLAYER);
        this.setKey(Keyboard.KEY_V);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            if (mc.player.fallDistance > 2) {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        }
    }

}
