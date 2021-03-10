package com.teddyhack.client.ui.clickgui.component.main;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import com.teddyhack.client.ui.clickgui.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    //variables moment 😞😞😞😞😞😞😞😞😞😞😞😎😎😛💬💬✅✅✅
    public int x, y, width, height;
    boolean isDragging;
    public int dragX, dragY;

    Category category;
    Minecraft mc = Minecraft.getMinecraft();
    public ArrayList<ModuleButton> moduleButtons;
    List<Module> modules;

    // CONSTRUCTOR 💪💪💪💪
    public Frame(Category category, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 240;
        this.dragX = 0;
        this.isDragging = false;
        this.category = category;
        this.modules = ModuleManager.getModulesByCategory(category);

        moduleButtons = new ArrayList<>();
        int offsetY = 14;
        for (Module module : ModuleManager.getModulesByCategory(category)) {
            moduleButtons.add(new ModuleButton(module, x, y + offsetY, this));
            offsetY += 14;
        }

        this.height = offsetY;
    }

    //function of render gui and clik
    public void render(int MouseX, int MouseY) {
        Gui.drawRect(x, y, x + width, y + 13, new Color(120,63,4).getRGB());
        if (category.open) {
            Gui.drawRect(x, y + 13, x + width, y + height, new Color(0, 0, 0, 100).getRGB());
        }
        mc.fontRenderer.drawString(category.toString(), x + 2, y + 2, new Color(255,255,255).getRGB());
        if (category.open) {
            for (ModuleButton moduleButton : moduleButtons) {
                updateComponent(MouseX, MouseY);
                moduleButton.draw(MouseX, MouseY);
            }
        }
    }
    public void onClick(int MouseX, int MouseY, int button) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.onClick(MouseX, MouseY, button);
        }

        if (onButton(MouseX, MouseY) && button == 1) {
            category.open = !category.open;
        }
    }

    public boolean onButton(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) { this.x = x; }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<ModuleButton> getModuleButtons() {
        return moduleButtons;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if(this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public void updateComponent(int mouseX, int mouseY) {
        int yOffset = 14;
        for (ModuleButton mb : moduleButtons) {
            mb.y = this.getY() + yOffset;
            mb.x = this.getX();
            yOffset += 14;
        }
    }

}