package com.teddyhack.ui.clickgui;

import com.lukflug.panelstudio.mc12.MinecraftGUI;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Theme;

public abstract class ClickGUI extends MinecraftGUI {

    private Toggleable colorToggle;
    private GUIInterface guiInterface;
    private Theme theme;
    private ClickGUI gui;

    public ClickGUI() {

    }
}
