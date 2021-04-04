package com.teddyware.client.module.combat;

import com.teddyware.api.event.events.EventPlayerUpdate;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.ModeSetting;
import com.teddyware.client.setting.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Offhand", description = "Offhand utilities for crystals or gapples.", key = Keyboard.KEY_NONE, category = Category.Combat)
public class Offhand extends Module {

    public NumberSetting totemHP = new NumberSetting("TotemHP", this, 10, 0, 20, 1);
    public ModeSetting OHItem = new ModeSetting("OHItem", this, "Crystal", "Crystal", "Gapple");

    public Offhand() {
        this.addSetting(totemHP, OHItem);
    }

    public boolean switching = false;
    public int lastSlot;

    @Override
    public void onUpdate() {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {

            if (switching) {
                swapItems(lastSlot, 2);
                return;
            }

            if (mc.player.getHeldItemOffhand().getItem() != getItemToSwitch()) {
                swapItems(getItemSlot(), 0);
            }
        }
    }

    public int getItemSlot() {
        if (getItemToSwitch() == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            final ItemStack item = mc.player.inventoryContainer.getInventory().get(i);

            if (item.isEmpty()) {
                continue;
            }

            if(item.getItem() == getItemToSwitch()) {
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

    public Item getItemToSwitch() {
        if (totemHP.getValue() >= mc.player.getHealth()) {
            return Items.TOTEM_OF_UNDYING;
        } else {
            if (OHItem.is("Crystal")) {
                return Items.END_CRYSTAL;
            }
            return Items.GOLDEN_APPLE;
        }
    }

}
