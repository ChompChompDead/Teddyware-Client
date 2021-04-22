package com.chompchompdead.teddyware.client.module.client;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import com.chompchompdead.teddyware.client.setting.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "CustomFont", description = "Replaces client text with a custom font.", key = Keyboard.KEY_NONE, category = Category.Client)
public class CustomFont extends Module {
    public static Module CustomFont = null;
    public static ModeSetting font = new ModeSetting("Font", CustomFont, "Lato", "Lato", "Raleway", "Ubuntu", "Verdana", "Comfortaa", "ComicSans");
    public static BooleanSetting overrideAll = new BooleanSetting("OverrideAll", CustomFont, false);

    public CustomFont() {
        this.addSetting(font, overrideAll);
    }

    public void onEnable() {

    }


    public void onDisable() {

    }

    @Override
    public String getArrayListInfo() {
        return font.getMode();
    }
}
