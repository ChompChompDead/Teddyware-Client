package com.chompchompdead.teddyware.client.module.movement;

import com.chompchompdead.teddyware.api.event.events.EventMove;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "SafeWalk", description = "Makes yourself not able to walk off the edge of blocks.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class SafeWalk extends Module {

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
