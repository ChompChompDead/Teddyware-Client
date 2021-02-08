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
        if (this.isToggled()) {
            super.onEnable();
            System.out.println(this.isToggled());
            mc.player.jump();
            if (mc.gameSettings.keyBindJump.isPressed()) {
                mc.player.motionY += 0.2;
            }
            if (mc.gameSettings.keyBindSneak.isPressed()) {
                mc.player.motionY -= 0.2;
            }
            if (mc.gameSettings.keyBindForward.isPressed()) {
                mc.player.capabilities.setFlySpeed((float) 1.5);
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.player.capabilities.isFlying = false;
    }
}
