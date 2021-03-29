package com.teddyware.client.module.player;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "take no fall damage ez.", Keyboard.KEY_NONE, Category.Player);
    }

    @Override
    public void onUpdate() {
        if (mc.player.fallDistance > 2) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }

}
