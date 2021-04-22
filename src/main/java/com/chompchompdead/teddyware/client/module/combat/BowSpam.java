package com.chompchompdead.teddyware.client.module.combat;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "BowSpam", description = "Releases and holds arrows as quick as you can.", key = Keyboard.KEY_NONE, category = Category.Combat)
public class BowSpam extends Module {
    @Override
    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
            mc.player.stopActiveHand();
        }
    }
}
