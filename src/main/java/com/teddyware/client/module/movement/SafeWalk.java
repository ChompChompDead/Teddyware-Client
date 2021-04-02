package com.teddyware.client.module.movement;

import com.teddyware.api.event.events.EventMove;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {

    public SafeWalk() {
        super("SafeWalk", "Don't fall off blocks", Keyboard.KEY_NONE, Category.Movement);
    }

    int height = 1;

    @EventHandler
    private final Listener<EventMove> eventMoveListener = new Listener<>(event -> {
        double x = event.x, y = event.y, z = event.z;

        if (mc.player.onGround && !mc.player.noClip) {
            double increment;
            for (increment = 0.05D; x != 0.0D && isOffsetBBEmpty(x, -this.height, 0.0D);) {
                if (x < increment && x >= -increment) {
                    x = 0.0D;
                } else if (x > 0.0D) {
                    x -= increment;
                } else {
                    x += increment;
                }
            }

            for (; z != 0.0D && isOffsetBBEmpty(0.0D, - this.height, z);) {
                if (z < increment && z >= -increment) {
                    z = 0.0D;
                } else if (z > 0.0D) {
                    z -= increment;
                } else {
                    z += increment;
                }
            }

            for (; x != 0.0D && z != 0.0D && isOffsetBBEmpty(x, - this.height, z);) {
                if (x < increment && x >= -increment) {
                    x = 0.0D;
                } else if (x > 0.0D) {
                    x -= increment;
                } else {
                    x += increment;
                }

                if (z < increment && z >= -increment) {
                    z = 0.0D;
                } else if (z > 0.0D) {
                    z -= increment;
                } else {
                    z += increment;
                }
            }
        }

        event.x = x;
        event.y = y;
        event.z = z;
        event.cancel();
    });

    boolean isOffsetBBEmpty(double x, double y, double z) {
        return mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(x, y, z)).isEmpty();
    }

}
