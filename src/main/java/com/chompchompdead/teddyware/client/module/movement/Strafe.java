package com.chompchompdead.teddyware.client.module.movement;

import com.chompchompdead.teddyware.api.event.events.EventMove;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

/**
* @author TrvsF
* pasted from wp2. sorry
 */

@Module.Data(name = "Strafe", description = "Move with modified speeds and strafes.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class Strafe extends Module {

    public Strafe() {
        this.addSetting(onWater, autoJump, backwards);
    }

    public BooleanSetting onWater = new BooleanSetting("OnWater", this, true);
    public BooleanSetting autoJump = new BooleanSetting("AutoJump", this, true);
    public BooleanSetting backwards = new BooleanSetting("Backwards", this, true);

    @Override
    public void onUpdate() {
        if (mc.player.isRiding()) return;
        if (mc.player.isInWater() || mc.player.isInLava()) {
            if (!onWater.isEnabled()) return;
        }

        if (mc.player.moveForward != 0 || mc.player.moveStrafing != 0) {

            if (mc.player.moveForward < 0 && !backwards.isEnabled()) return;
            if (mc.player.onGround) {
                if (autoJump.isEnabled()) {
                    mc.player.motionY = 0.405f;
                }

                final float yaw = getRotationYaw();
                mc.player.motionX -= MathHelper.sin(yaw) * 0.2f;
                mc.player.motionZ += MathHelper.cos(yaw) * 0.2f;
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.4, mc.player.posZ, false));
            }
        }
        if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
            mc.player.motionY = 0.405f;
        }
    }

    @EventHandler
    private final Listener<EventMove> eventMoveListener = new Listener<>(event -> {
        if (mc.player.isSneaking() || mc.player.isOnLadder() || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) return;

        float speed = 0.2873f;
        float moveForward = mc.player.movementInput.moveForward;
        float moveStrafe = mc.player.movementInput.moveStrafe;
        float rotationYaw = mc.player.rotationYaw;

        if (mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amp = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            speed *= (1.2f * (amp+1));
        }
        speed *= 1.0064f;

        if (moveForward == 0 && moveStrafe == 0) {
            event.setX(0.0D);
            event.setZ(0.0D);
        } else {
            if (moveForward != 0.0f) {
                if (moveStrafe > 0.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                } else if (moveStrafe< 0.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0f) {
                    moveForward = 1.0f;
                } else if (moveForward < 0.0f) {
                    moveForward = -1.0f;
                }
            }
        }
        event.setX((moveForward * speed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))) + (moveStrafe * speed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))));
        event.setZ((moveForward * speed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))) - (moveStrafe * speed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))));
    });

    private float getRotationYaw() {
        float rotation_yaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * 0.017453292f;
    }

}
