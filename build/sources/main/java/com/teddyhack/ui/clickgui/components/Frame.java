package com.teddyhack.ui.clickgui.components;

import com.teddyhack.Client;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    //variables moment 😞😞😞😞😞😞😞😞😞😞😞😎😎😛💬💬✅✅✅
    int x, y, width, height;

    Category category;
    Minecraft mc = Minecraft.getMinecraft();
    ArrayList<ModuleButton> moduleButtons;
    List<Module> modules;

    // CONSTRUCTOR 💪💪💪💪
    public Frame(Category category, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 240;
        this.category = category;
        this.modules = ModuleManager.getModulesByCategory(category);

        moduleButtons = new ArrayList<>();
        int offsetY = 14;
        for (Module module : ModuleManager.getModulesByCategory(category)) {
            moduleButtons.add(new ModuleButton(module, x, y + offsetY, this));
            offsetY += 14;
        }
    }

    //function of render gui and clik
    public void render(int MouseX, int MouseY) {
        Gui.drawRect(x, y, x + width, y + 13, new Color(120,63,4).getRGB());
        Gui.drawRect(x, y + 13, x + width, y + modules.size() * 14 + 12, new Color(0,0,0,100).getRGB());
        mc.fontRenderer.drawString(category.toString(), x + 2, y + 2, new Color(255,255,255).getRGB());
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.draw(MouseX, MouseY);
        }
    }
    public void onClick(int x, int y, int button) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.onClick(x, y, button);
        }
    }

}
