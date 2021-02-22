package com.teddyhack.ui.clickgui;

import com.lukflug.panelstudio.mc12.MinecraftGUI;
import com.lukflug.panelstudio.mc12.MinecraftHUDGUI;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.GameSenseTheme;
import com.lukflug.panelstudio.theme.SettingsColorScheme;
import com.lukflug.panelstudio.theme.Theme;
import com.teddyhack.module.client.ColorMain;
import com.teddyhack.ui.UIRenderer;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public abstract class ClickGUI extends MinecraftHUDGUI {

    private Toggleable colorToggle;
    private GUIInterface guiInterface;
    private Theme theme;
    private ClickGUI gui;

    public ClickGUI() {
        colorToggle = new Toggleable() {
            @Override
            public void toggle() {
                ColorMain.colorModel.increment();
            }

            @Override
            public boolean isOn() {
                return ColorMain.colorModel.is("RGB");
            }
        };

        guiInterface = new GUIInterface(true) {
            @Override
            protected String getResourcePrefix() {
                return "teddyhack:/";
            }

            @Override
            public void drawString(Point pos, String s, Color c) {
                end();
                mc.fontRenderer.drawStringWithShadow(s, pos.x, pos.y, -1);
                begin();
            }

            @Override
            public int getFontWidth(String s) {
                return 0;
            }

            @Override
            public int getFontHeight() {
                return 0;
            }
        };
        //theme = new GameSenseTheme(new SettingsColorScheme(ClickGUIModule.INSTANCE.enabledColor, ClickGUIModule.INSTANCE.backgroundColor,ClickGUIModule.INSTANCE.settingBackgroundColor, ClickGUIModule.INSTANCE.outlineColor,ClickGUIModule.INSTANCE.fontColor,ClickGUIModule.INSTANCE.opacity), height, 2);

    }
}
