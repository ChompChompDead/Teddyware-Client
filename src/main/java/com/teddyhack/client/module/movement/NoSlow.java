package com.teddyhack.client.module.movement;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.listeners.EventUpdate;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "no slow", Keyboard.KEY_NONE, Category.Movement);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                /*
                if (mc.player.isHandActive() && mc.world != null && mc.player != null) {
                    if (mc.player.isHandActive() && !mc.player.isRiding()) {
                        mc.player.movementInput.moveStrafe /= 0.2F;
                        mc.player.movementInput.moveForward /= 0.2F;
                    }

                    if (mc.player.isSneaking() && mc.player.isSneaking()) {
                        mc.player.movementInput.moveStrafe = (float) ((double) mc.player.movementInput.moveStrafe / 0.3D);
                        mc.player.movementInput.moveForward = (float) ((double) mc.player.movementInput.moveForward / 0.3D);
                    }
                }
                */
            }
        }
    }

    @SubscribeEvent
    public void onUseItem(PlayerInteractEvent.RightClickItem event) {
        Item item = mc.player.getHeldItem(event.getHand()).getItem();
        if ((item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion)) {
            mc.player.setSneaking(false);
            mc.player.motionX *= 5;
            mc.player.motionZ *= 5;
        }
    }

    public void onEnable() { super.onEnable(); }

    public void onDisable() { super.onDisable(); }
}
