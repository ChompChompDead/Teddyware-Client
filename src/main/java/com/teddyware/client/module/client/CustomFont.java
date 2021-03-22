package com.teddyware.client.module.client;

import com.teddyware.api.util.font.CustomFontRenderer;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CustomFont extends Module {
    public static CustomFont INSTANCE;

    public ModeSetting font = new ModeSetting("Font", this, "Verdana", "Verdana", "Arial", "Comic Sans Ms", "Helvetica");

    public CustomFont() {
        super("CustomFont", "Changes GUI font.", Keyboard.KEY_NONE, Category.Client);
        this.addSetting(font);
        INSTANCE = this;
    }

    public void onEnable() {
        if(font.is("Comic Sans Ms")) {
            Teddyware.instance.customFontRenderer = new CustomFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), true, true);
        }

        if(font.is("Arial")) {
            Teddyware.instance.customFontRenderer = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
        }

        if(font.is("Verdana")) {
            Teddyware.instance.customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
        }

        if (font.is("Helvetica")) {
            Teddyware.instance.customFontRenderer = new CustomFontRenderer(new Font("Helvetica", Font.PLAIN, 18), true, true);

        }
    }

    public void onDisable() {

    }
}
