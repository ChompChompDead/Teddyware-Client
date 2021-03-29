package com.teddyware.client.module.combat;

import com.teddyware.api.util.BurrowUtil;
import com.teddyware.api.util.ChatUtil;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.BooleanSetting;
import com.teddyware.client.setting.settings.NumberSetting;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class Burrow extends Module {

    public BooleanSetting rotate = new BooleanSetting("Rotate", this, false);
    public NumberSetting offset = new NumberSetting("Offset", this, 7.0F, -20.0F, 20.0F, 1);

    private BlockPos originalPos;
    private int oldSlot = -1;

    public Burrow() {
        super("Burrow", "bad meta", Keyboard.KEY_NONE, Category.Combat);
        this.addSetting(rotate, offset);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) ||
                intersectsWithEntity(this.originalPos)) {
            toggle();
            return;
        }

        oldSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        if (BurrowUtil.findHotbarBlock(BlockObsidian.class) == -1) {
            ChatUtil.type("Can't find obsidian in hotbar!");
            toggle();
            return;
        }

        BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockObsidian.class));

        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));

        BurrowUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, rotate.isEnabled(), true, false);
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset.getValue(), mc.player.posZ, false));

        BurrowUtil.switchToSlot(oldSlot);

        toggle();
    }

    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }
        return false;
    }
}
