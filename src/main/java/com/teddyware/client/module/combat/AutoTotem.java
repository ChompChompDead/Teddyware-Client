package com.teddyware.client.module.combat;

import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventUpdate;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class AutoTotem extends Module {
    
    public AutoTotem() {
        super("AutoTotem", "auto totem :)", Keyboard.KEY_NONE, Category.Combat);
    }

    public boolean switching = false;
    public int lastSlot;

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {

                    if (switching) {
                        swapItems(lastSlot, 2);
                        return;
                    }

                    if (mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                        swapItems(getItemSlot(), 0);
                    }
                }
            }
        }
    }

    public int getItemSlot() {
        if (Items.TOTEM_OF_UNDYING == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public void swapItems(int slot, int step) {
        if (slot == -1) return;
        if (step == 0) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        }
        if (step == 1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = true;
            lastSlot = slot;
        }
        if (step == 2) {
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = false;
        }

        mc.playerController.updateController();
    }
}
