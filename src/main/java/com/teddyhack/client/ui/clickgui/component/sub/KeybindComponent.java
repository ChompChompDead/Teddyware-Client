package com.teddyhack.client.ui.clickgui.component.sub;

import com.teddyhack.client.ui.clickgui.component.Component;
import com.teddyhack.client.ui.clickgui.component.main.ModuleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class KeybindComponent extends Component {

    private boolean binding;
    int x,y,width,height;
    int offset = 14;

    ModuleButton parent;
    Minecraft mc = Minecraft.getMinecraft();
    ArrayList<ModuleButton> moduleButtons;

    public KeybindComponent(int x, int y, ModuleButton parent) {
        this.x = x;
        this.y = y;
        this.width = parent.parent.width;
        this.height = 14;
        this.parent = parent;
        this.moduleButtons = parent.parent.getModuleButtons();
    }

    public void draw(int mouseX, int mouseY) {
        if (parent.settingOpen) {
            Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, new Color(0,0,0, 100).getRGB());
        }
    }
}
