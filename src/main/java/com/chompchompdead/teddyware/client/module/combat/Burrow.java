package com.chompchompdead.teddyware.client.module.combat;

import com.chompchompdead.teddyware.api.util.BlockUtil;
import com.chompchompdead.teddyware.api.util.ChatUtil;
import com.chompchompdead.teddyware.api.util.InventoryUtil;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import com.chompchompdead.teddyware.client.setting.settings.ModeSetting;
import com.chompchompdead.teddyware.client.setting.settings.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

/**
 * @author Ciruu
 * a bit different as there are block modes, still skidded :)
 */

@Module.Data(name = "Burrow", description = "Places a block on your lower body. Works best in holes.", key = Keyboard.KEY_NONE, category = Category.Combat)
public class Burrow extends Module {

    public ModeSetting block = new ModeSetting("Block", this, "Obby", "EChest", "Obby");
    public BooleanSetting rotate = new BooleanSetting("Rotate", this, false);
    public NumberSetting offset = new NumberSetting("Offset", this, 7.0F, 0.0F, 20.0F, 1);

    private BlockPos originalPos;
    private int oldSlot = -1;
    private Vec3d centeredBlock = Vec3d.ZERO;

    public Burrow() {
        this.addSetting(block, rotate, offset);
    }

    @Override
    public void onEnable() {
        originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(getBurrowModeBlock()) ||
                intersectsWithEntity(this.originalPos)) {
            toggle();
            return;
        }

        if (mc.player.onGround) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }

        centeredBlock = getCenterOfBlock(mc.player.posX, mc.player.posY, mc.player.posZ);
        oldSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        if (InventoryUtil.findHotbarBlock(getBurrowModeClass()) == -1) {
            ChatUtil.type("No " + getBurrowModeString() + " in hotbar, toggling...");
            toggle();
            return;
        }
    }
    
    @Override
    public void onUpdate() {
        if (InventoryUtil.findHotbarBlock(getBurrowModeClass()) == 1) {
            ChatUtil.type(getBurrowModeString() + " In hotbar, burrowing...");
            toggle();
            return;
        }
        center();
        InventoryUtil.switchToSlot(InventoryUtil.findHotbarBlock(getBurrowModeClass()));

        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));

        BlockUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, rotate.isEnabled(), true, false);
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset.getValue(), mc.player.posZ, false));

        InventoryUtil.switchToSlot(oldSlot);
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

        toggle();
    }

    private Class getBurrowModeClass() {
        if (block.is("EChest")) {
            return BlockEnderChest.class;
        }
        return BlockObsidian.class;
    }

    private Block getBurrowModeBlock() {
        if (block.is("EChest")) {
            return Blocks.ENDER_CHEST;
        }
        return Blocks.OBSIDIAN;
    }

    private String getBurrowModeString() {
        if (block.is("EChest")) {
            return "ender chest";
        }
        return "obsidian";
    }

    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }
        return false;
    }

    private Vec3d getCenterOfBlock(double playerX, double playerY, double playerZ) {

        double newX = Math.floor(playerX) + 0.5;
        double newY = Math.floor(playerY);
        double newZ = Math.floor(playerZ) + 0.5;

        return new Vec3d(newX, newY, newZ);
    }

    private void center() {
        if (centeredBlock != Vec3d.ZERO && mc.player.onGround) {
            double xDeviation = Math.abs(centeredBlock.x - mc.player.posX);
            double zDeviation = Math.abs(centeredBlock.z - mc.player.posZ);

            if (xDeviation <= 0.1 && zDeviation <= 0.1){
                centeredBlock = Vec3d.ZERO;
            } else {
                double newX;
                double newZ;

                if (mc.player.posX > Math.round(mc.player.posX)) {
                    newX = Math.round(mc.player.posX) + 0.5;
                } else if (mc.player.posX < Math.round(mc.player.posX)) {
                    newX = Math.round(mc.player.posX) - 0.5;
                } else {
                    newX = mc.player.posX;
                }

                if (mc.player.posZ > Math.round(mc.player.posZ)) {
                    newZ = Math.round(mc.player.posZ) + 0.5;
                } else if (mc.player.posZ < Math.round(mc.player.posZ)) {
                    newZ = Math.round(mc.player.posZ) - 0.5;
                } else {
                    newZ = mc.player.posZ;
                }

                mc.player.connection.sendPacket(new CPacketPlayer.Position(newX, mc.player.posY, newZ, true));
                mc.player.setPosition(newX, mc.player.posY, newZ);
            }
        }
    }

    @Override
    public String getArrayListInfo() {
        return block.getMode();
    }
}
