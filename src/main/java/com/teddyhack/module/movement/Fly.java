package com.teddyhack.module.movement;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {

    private Minecraft mc = Minecraft.getMinecraft();

    public Fly() {
        super("Fly", "Fly lolololo", Category.MOVEMENT);
        this.setKey(Keyboard.KEY_R);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.player.jump();
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.allowFlying = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.allowFlying = false;
    }
}
