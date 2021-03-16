package com.teddyhack.client.module.client;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventUpdate;
import com.teddyhack.api.util.font.CustomFontRenderer;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.setting.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CustomFont extends Module {
    public ModeSetting font = new ModeSetting("Font", this, "Verdana", "Verdana", "Arial", "Comic Sans Ms");

    public CustomFont() {
        super("CustomFont", "Custom font :D", Keyboard.KEY_NONE, Category.Client);
        this.addSetting(font);
    }

    public void onEnable() {
        if(font.is("Comic Sans Ms")) {
            Teddyhack.instance.customFontRenderer = new CustomFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), true, true);
        }

        if(font.is("Arial")) {
            Teddyhack.instance.customFontRenderer = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
        }

        if(font.is("Verdana")) {
            Teddyhack.instance.customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
        }
    }

    public void onDisable() {

    }
}
