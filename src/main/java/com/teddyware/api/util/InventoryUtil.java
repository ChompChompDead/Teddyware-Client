package com.teddyware.api.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketHeldItemChange;

/**
 * @author Ciruu
 * split files from burrowutil
 */

public class InventoryUtil implements UtilInterface {

    public static int findHotbarBlock(Class clazz) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) {
                continue;
            }

            if (clazz.isInstance(stack.getItem())) {
                return i;
            }

            if (stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (clazz.isInstance(block)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findHotbarItem(Class clazz) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) {
                continue;
            }

            if (clazz.isInstance(stack.getItem())) {
                return i;
            }

            if (stack.getItem() instanceof ItemSword) {
                ItemSword item = ((ItemSword) stack.getItem());
                if (clazz.isInstance(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void switchToSlot(final int slot) {
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }

}
