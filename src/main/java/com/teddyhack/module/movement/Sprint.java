package com.teddyhack.module.movement;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    private Minecraft mc = Minecraft.getMinecraft();

    public Sprint() {
        super("Sprint", "Automatically run without holding your run hotkey.", Category.MOVEMENT);
        this.setKey(Keyboard.KEY_C);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (mc.player.movementInput.moveForward >= 0) {
            mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.player.setSprinting(false);
    }
}
