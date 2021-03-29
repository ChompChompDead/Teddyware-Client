package com.teddyware.client.module.movement;

import com.teddyware.api.event.events.EventLiquidCollisionBB;
import com.teddyware.api.util.EntityUtil;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

public class Jesus extends Module {

    public Jesus() {
        super("Jesus", "walk on water", Keyboard.KEY_NONE, Category.Movement);
    }

    public float offset = 0.5f;

    @Override
    public void onUpdate() {
        if(toggled) {
            if (!mc.player.isSneaking() && !mc.player.noClip && !mc.gameSettings.keyBindJump.isKeyDown() && EntityUtil.isInLiquid()) {
                mc.player.motionY = 0.1f;
            }
        }
    }

    @EventHandler
    private final Listener<EventLiquidCollisionBB> getLiquidCollisionBB = new Listener<>(event -> {
        if(toggled) {
            if (mc.world != null && mc.player != null) {
                if (this.checkCollide() && !(mc.player.motionY >= 0.1f) && event.getBlockPos().getY() < mc.player.posY - this.offset) {
                    if (mc.player.getRidingEntity() != null) {
                        event.setBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 1 - this.offset, 1));
                    } else {
                        event.setBoundingBox(Block.FULL_BLOCK_AABB);
                    }
                }
                event.cancel();
            }
        }
    });

    private boolean checkCollide() {
        if (mc.player.isSneaking()) {
            return false;
        }

        if (mc.player.getRidingEntity() != null) {
            if (mc.player.getRidingEntity().fallDistance >= 3.0f) {
                return false;
            }
        }

        if (mc.player.fallDistance >= 3.0f) {
            return false;
        }

        return true;
    }
}