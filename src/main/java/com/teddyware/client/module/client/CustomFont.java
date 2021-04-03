package com.teddyware.client.module.client;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "CustomFont", description = "Replaces client text with a custom font.", key = Keyboard.KEY_NONE, category = Category.Client)
public class CustomFont extends Module {
    public static Module CustomFont = null;
    public static ModeSetting font = new ModeSetting("Font", CustomFont, "Lato", "Lato", "Raleway", "Ubuntu", "LexendDeca", "Verdana", "Comfortaa", "Comic Sans");

    public CustomFont() {
        this.addSetting(font);
    }

    public void onEnable() {

    }


    public void onDisable() {

    }
}
