package com.teddyware.client.module.client;

import com.teddyware.api.util.color.JColor;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.BooleanSetting;
import com.teddyware.client.setting.settings.ColorSetting;
import com.teddyware.client.setting.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "ArrayList", description = "Shows which modules are toggled on a GUI.", key = Keyboard.KEY_NONE, category = Category.Client)
public class ArrayListt extends Module {

    public static Module ArrayListt = null;

    public static BooleanSetting modeInfo = new BooleanSetting("ModeInfo", ArrayListt, false);
    public static BooleanSetting multiRainbow = new BooleanSetting("MultiRainbow", ArrayListt, true);
    public static ColorSetting color = new ColorSetting("Color", ArrayListt, new JColor(255, 255, 255));
    public static ModeSetting mode = new ModeSetting("Sort", ArrayListt, "Up", "Down", "Up");

    public ArrayListt() {
        this.addSetting(modeInfo, multiRainbow, color, mode);
        this.arrayListInfo = mode.getMode();
    }

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

}
