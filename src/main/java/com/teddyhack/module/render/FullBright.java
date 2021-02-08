package com.teddyhack.module.render;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {

    private Minecraft mc = Minecraft.getMinecraft();

    public FullBright() {
        super("FullBright", "epic brightness", Category.RENDER);
        this.setKey(Keyboard.KEY_G);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1;
    }
}
