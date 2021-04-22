package com.chompchompdead.teddyware.client.module.player;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "FastUse", description = "Right click items and blocks quickly.", key = Keyboard.KEY_NONE, category = Category.Player)
public class FastUse extends Module {
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
