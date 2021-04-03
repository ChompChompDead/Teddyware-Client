package com.teddyware.client.module.render;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;


@Module.Data(name = "FullBright", description = "Adjust brightness levels to your own choice.", key = Keyboard.KEY_NONE, category = Category.Render)
public class FullBright extends Module {

    public NumberSetting brightness = new NumberSetting("Brightness", this, 1F, 0F, 2F, 0.1F);

    public FullBright() {
        this.addSetting(brightness);
    }

    @Override
    public void onUpdate() {
        Arrays.fill(mc.world.provider.getLightBrightnessTable(), (float) brightness.getValue());
    }
}