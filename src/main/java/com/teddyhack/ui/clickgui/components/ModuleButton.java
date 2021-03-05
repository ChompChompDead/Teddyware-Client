package com.teddyhack.ui.clickgui.components;

import com.teddyhack.Client;
import com.teddyhack.event.listeners.EventNotifier;
import com.teddyhack.module.Module;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ModuleButton {

    int x, y, width, height;

    Module module;
    Frame parent;
    Minecraft mc = Minecraft.getMinecraft();

    public ModuleButton(Module module, int x, int y, Frame parent) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.width = parent.width;
        this.height = 14;
    }

    public void draw(int MouseX, int MouseY) {
        if (module.toggled) {
            mc.fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(120,63,4).getRGB());
        } else {
            mc.fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(255, 255, 255).getRGB());
        }
    }

    public void onClick(int x, int y, int button) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            module.toggle();
            Client.onEvent(new EventNotifier(module.name, module.toggled));
        }
    }

}
