package com.chompchompdead.teddyware.client.module.movement;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "LongFly", description = "Manipulates your jump to be long.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class LongFly extends Module {

    @Override
    public void onUpdate() {
        if(mc.player.moveForward > 0 && !mc.player.isSneaking()){
            mc.player.setSprinting(true);
            mc.player.jump();

            if (mc.player.fallDistance > 1) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
        }
    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    }
}
