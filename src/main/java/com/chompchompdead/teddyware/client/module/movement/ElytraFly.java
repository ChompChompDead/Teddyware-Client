package com.chompchompdead.teddyware.client.module.movement;

import com.chompchompdead.teddyware.api.event.events.EventPacket;
import com.chompchompdead.teddyware.api.event.events.EventTravel;
import com.chompchompdead.teddyware.api.util.ChatUtil;
import com.chompchompdead.teddyware.api.util.MathUtil;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.api.util.Timer;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import com.chompchompdead.teddyware.client.setting.settings.ModeSetting;
import com.chompchompdead.teddyware.client.setting.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "ElytraFly", description = "Fly with an elytra.", key = Keyboard.KEY_NONE, category = Category.Movement)
public class ElytraFly extends Module {

    public ModeSetting mode = new ModeSetting("Mode", this, "Normal", "Normal", "Superior", "Control");
    public NumberSetting speed = new NumberSetting("Speed", this, 1.82D, 0D, 10D, 1D);
    public NumberSetting glideSpeed = new NumberSetting("GlideSpeed", this, 1D, 0D, 10D, 1D);
    public NumberSetting upSpeed = new NumberSetting("UpSpeed", this, 2D, 0D, 10D, 1D);
    public NumberSetting downSpeed = new NumberSetting("DownSpeed", this, 1.82D, 0D, 10D, 1D);
    public BooleanSetting accelerate = new BooleanSetting("Accelerate", this, true);
    public NumberSetting accelTimer = new NumberSetting("AccelTimer", this, 1D, 0D, 10D, 1);
    public NumberSetting rotationPitch = new NumberSetting("RotatePitch", this, 0D, -90D, 90D, 1D);
    public BooleanSetting cancelInWater = new BooleanSetting("CancelInWater", this, true);
    public NumberSetting cancelAtHeight = new NumberSetting("CancelAtHeight", this, 5D, 0D, 10D, 1D);
    public BooleanSetting instantFlight = new BooleanSetting("InstantFlight", this, true);
    public BooleanSetting equipElytra = new BooleanSetting("EquipElytra", this, false);
    public BooleanSetting pitchSpoof = new BooleanSetting("PitchSpoof", this, false);

    private final Timer accelerationTimer = new Timer();
    private final Timer accelerationResetTimer = new Timer();
    private final Timer instantFlightTimer = new Timer();
    private boolean sendMessage = false;
    private int elytraSlot = -1;

    public ElytraFly() {
        this.addSetting(mode, speed, glideSpeed, upSpeed, downSpeed, cancelAtHeight, cancelInWater, accelerate, accelTimer, rotationPitch, instantFlight, equipElytra, pitchSpoof);
    }

    @EventHandler
    private final Listener<EventTravel> eventTravelListener = new Listener<>(event -> {
        if(mc.player == null) return;
        if(mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) return;

        if (!mc.player.isElytraFlying()) {
            if(!mc.player.onGround && instantFlight.isEnabled() && instantFlightTimer.passed(1000)) {
                instantFlightTimer.reset();
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            return;
        }

        switch (mode.getMode()) {
            case "Normal": handleNormal(event); break;
            case "Superior": handleSuperior(event); break;
            case "Control": handleControl(event); break;
            default: break;
        }
    });

    @EventHandler
    private final Listener<EventPacket> eventPacketListener = new Listener<>(event -> {
        Packet packet = event.getPacket();

        if (packet instanceof CPacketPlayer && pitchSpoof.isEnabled() && mc.player.isElytraFlying()) {
            if (packet instanceof CPacketPlayer.PositionRotation) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.onGround));
                event.cancel();
            } else if (packet instanceof CPacketPlayer.Rotation) {
                event.cancel();
            }
        }
    });

    @Override
    public void onEnable() {
        super.onEnable();

        elytraSlot = -1;
        if(equipElytra.isEnabled()) {
            if(mc.player != null && mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
                for(int i = 0; i < 44; i++) {
                    ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                    if(itemStack.isEmpty() || itemStack.getItem() != Items.ELYTRA) continue;

                    ItemElytra itemElytra = (ItemElytra) itemStack.getItem();
                    elytraSlot = i;
                    break;
                }
                if(elytraSlot != -1) {
                    boolean armorChest = mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.AIR;
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, elytraSlot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
                    if(armorChest) mc.playerController.windowClick(mc.player.inventoryContainer.windowId, elytraSlot, 0, ClickType.PICKUP, mc.player);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if(mc.player == null) return;
        if(elytraSlot != -1) {
            boolean hasItem = !mc.player.inventory.getStackInSlot(elytraSlot).isEmpty() || mc.player.inventory.getStackInSlot(elytraSlot).getItem() != Items.AIR;
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, elytraSlot, 0, ClickType.PICKUP, mc.player);
            if (hasItem) mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
        }
    }

    public void accelerate() {
        if (accelerationResetTimer.passed(accelerationTimer.getTime() * 1000)) {
            accelerationResetTimer.reset();
            accelerationTimer.reset();
            sendMessage = false;
        }
        float speed = (float) this.speed.getValue();
        double[] dir = MathUtil.directionSpeed(speed);

        boolean d = mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0;
        mc.player.motionX = d ? dir[0] : 0;
        mc.player.motionY = mc.player.movementInput.sneak ? -downSpeed.getValue() : -(glideSpeed.getValue() / 10000f);
        mc.player.motionZ = d ? dir[1] : 0;

        mc.player.prevLimbSwingAmount = 0;
        mc.player.limbSwingAmount = 0;
        mc.player.limbSwing = 0;
    }

    public void handleNormal(EventTravel event) {
        double height = mc.player.posY;
        if (height <= cancelAtHeight.getValue() && !sendMessage) {
            ChatUtil.type("You must go up because your y-height is lower than cancelAtHeight value.");
            sendMessage = true;
            return;
        }
        boolean moveKeyDown = mc.player.movementInput.moveForward > 0 || mc.player.movementInput.moveStrafe > 0;
        boolean _cancelInWater = !mc.player.isInWater() && !mc.player.isInLava() && cancelInWater.isEnabled();
        if (mc.player.movementInput.jump) {
            event.cancel();
            accelerate();
            return;
        }
        if(moveKeyDown) {
            accelerationTimer.resetTimeSkipTo(-accelerationTimer.getTime());
        } else if ((mc.player.rotationPitch <= rotationPitch.getValue()) && _cancelInWater && accelerate.isEnabled() && accelerationTimer.passed(accelerationTimer.getTime())) {
            accelerate();
            return;
        }
        event.cancel();
        accelerate();
    }

    public void handleSuperior(EventTravel event) {
        if (mc.player.movementInput.jump) {
            double motion = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
            if(motion > 1D) return;

            double[] dir = MathUtil.directionSpeedNoForward(speed.getValue());
            mc.player.motionX = dir[0];
            mc.player.motionY = -(glideSpeed.getValue() / 10000f);
            mc.player.motionZ = dir[1];

            event.cancel();
            return;
        }
        mc.player.setVelocity(0, 0, 0);

        double[] dir = MathUtil.directionSpeed(speed.getValue());
        if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
            mc.player.motionX = dir[0];
            mc.player.motionY = -(glideSpeed.getValue() / 10000f);
            mc.player.motionZ = dir[1];
        }
        if(mc.player.movementInput.sneak) mc.player.motionY = -downSpeed.getValue();
        mc.player.prevLimbSwingAmount = 0;
        mc.player.limbSwingAmount = 0;
        mc.player.limbSwing = 0;
    }

    public void handleControl(EventTravel event) {
        double[] dir = MathUtil.directionSpeed(speed.getValue());
        if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
            mc.player.motionX = dir[0];
            mc.player.motionZ = dir[1];
            mc.player.motionX -= (mc.player.motionX*(Math.abs(mc.player.rotationPitch)+90)/90) - mc.player.motionX;
            mc.player.motionZ -= (mc.player.motionZ*(Math.abs(mc.player.rotationPitch)+90)/90) - mc.player.motionZ;
        } else {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }
        mc.player.motionY = (-MathUtil.degToRad(mc.player.rotationPitch)) * mc.player.movementInput.moveForward;

        mc.player.prevLimbSwingAmount = 0;
        mc.player.limbSwingAmount = 0;
        mc.player.limbSwing = 0;
        event.cancel();
    }

    @Override
    public String getArrayListInfo() {
        return mode.getMode();
    }

}
