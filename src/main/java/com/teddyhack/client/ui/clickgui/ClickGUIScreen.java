package com.teddyhack.client.ui.clickgui;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.ui.clickgui.component.Frame;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUIScreen extends GuiScreen {

    //variableszzzz
    public static ClickGUIScreen INSTANCE = new ClickGUIScreen();

    boolean isDragging;
    ArrayList<Frame> frames;

    //constructor :DDD
    public ClickGUIScreen() {
        frames = new ArrayList<>();
        int offset = 0;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, 10 + offset, 20));
            offset += 110;
        }
    }

    //draw and click function
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (Frame frame : frames) {
            frame.render(mouseX, mouseY);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (Frame frame : frames) {
            frame.onClick(mouseX, mouseY, mouseButton);
        }
    }

}
