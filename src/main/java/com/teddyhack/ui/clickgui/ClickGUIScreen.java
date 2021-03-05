package com.teddyhack.ui.clickgui;

import com.teddyhack.ui.clickgui.components.Frame;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ClickGUI extends GuiScreen {

    public ClickGUIScreen INSTANCE = new ClickGUIScreen();

    ArrayList<Frame> frames;

    public ClickGUI() {
        frames = new ArrayList<>();
        for
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
