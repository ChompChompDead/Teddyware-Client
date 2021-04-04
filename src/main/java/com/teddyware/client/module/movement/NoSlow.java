package com.teddyware.client.module.movement;

import com.teddyware.api.event.events.EventMoveState;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.BooleanSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShield;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "NoSlow", description = "Makes you move when things make you slow.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class NoSlow extends Module {

    public BooleanSetting ice = new BooleanSetting("Ice", this, true);
    public BooleanSetting items = new BooleanSetting("Items", this, true);

    public NoSlow() {
        this.addSetting(items, ice);
    }

    @Override
    public void onDisable() {
        Blocks.ICE.setDefaultSlipperiness(0.98f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
    }

    @Override
    public void onUpdate() {
        if (mc.player.isHandActive()) {
            if (mc.player.getHeldItem(mc.player.getActiveHand()).getItem() instanceof ItemShield) {
                if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0 && mc.player.getItemInUseMaxCount() >= 8) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                }
            }
        }

        if (!ice.isEnabled()) return;

        if (mc.player.getRidingEntity() != null) {
            Blocks.ICE.setDefaultSlipperiness(0.98f);
            Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
            Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
        } else {
            Blocks.ICE.setDefaultSlipperiness(0.45f);
            Blocks.FROSTED_ICE.setDefaultSlipperiness(0.45f);
            Blocks.PACKED_ICE.setDefaultSlipperiness(0.45f);
        }
    }

    @EventHandler
    private final Listener<EventMoveState> eventMoveStateListener = new Listener<>(event -> {
        if (items.isEnabled() && mc.player.isHandActive() && !mc.player.isRiding()) {
            mc.player.movementInput.moveForward /= 0.2F;
            mc.player.movementInput.moveStrafe /= 0.2F;
        }
    });
}
