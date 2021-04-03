package com.teddyware.client.module.client;

import com.teddyware.api.util.color.JColor;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.BooleanSetting;
import com.teddyware.client.setting.settings.ColorSetting;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "ArrayList", description = "Shows which modules are toggled on a GUI.", key = Keyboard.KEY_NONE, category = Category.Client)
public class ArrayListt extends Module {

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

}
