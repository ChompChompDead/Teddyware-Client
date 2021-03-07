package com.teddyhack.client.ui.clickgui.component;

import com.teddyhack.client.Client;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    //variables moment 😞😞😞😞😞😞😞😞😞😞😞😎😎😛💬💬✅✅✅
    int x, y, width, height;
    boolean open = true;

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
        if (category.open) {
            Gui.drawRect(x, y + 13, x + width, y + modules.size() * 14 + 12, new Color(0, 0, 0, 100).getRGB());
        }
        mc.fontRenderer.drawString(category.toString(), x + 2, y + 2, new Color(255,255,255).getRGB());
        if (category.open) {
            for (ModuleButton moduleButton : moduleButtons) {
                moduleButton.draw(MouseX, MouseY);
            }
        }
    }
    public void onClick(int MouseX, int MouseY, int button) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.onClick(MouseX, MouseY, button);
        }

        if (onButton(MouseX, MouseY) && button == 1) {
            if (category.open) {
                category.open = false;
                Client.log.info("Closed");
            } else {
                category.open = true;
                Client.log.info("Open");
            }
        }
    }

    public boolean onButton(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

}