package com.teddyware.client.module.player;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", "Use stuff fast", Keyboard.KEY_NONE, Category.Player);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null || mc.world != null) {
            ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 0, "rightClickDelayTimer", "field_71467_ac");
        }
    }

    @Override
    public void onDisable() {
        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 4, "rightClickDelayTimer", "field_71467_ac");
    }
}
