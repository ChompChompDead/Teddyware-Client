package com.chompchompdead.teddyware.client.module.player;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "NoFall", description = "Take no fall damage.", key = Keyboard.KEY_NONE, category = Category.Player)
public class NoFall extends Module {
    @Override
    public void onUpdate() {
        if (mc.player.fallDistance > 2) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }

}
