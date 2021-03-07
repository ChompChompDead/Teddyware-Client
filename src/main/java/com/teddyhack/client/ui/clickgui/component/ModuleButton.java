package com.teddyhack.client.ui.clickgui.component;

import com.teddyhack.client.Client;
import com.teddyhack.api.event.listeners.EventNotifier;
import com.teddyhack.client.module.Module;
import net.minecraft.client.Minecraft;

import java.awt.Color;

public class ModuleButton {

    // WTF ANOTHER VARIABLE???
    int x, y, width, height;

    Module module;
    Frame parent;
    Minecraft mc = Minecraft.getMinecraft();

    // (contrsuctor)
    public ModuleButton(Module module, int x, int y, Frame parent) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.width = parent.width;
        this.height = 14;
    }

    //draw and click im cool your not stupid thot
    public void draw(int MouseX, int MouseY) {
        if (module.toggled) {
            mc.fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(82, 82, 82).getRGB());
        } else {
            mc.fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(255, 255, 255).getRGB());
        }
    }

    public void onClick(int MouseX, int MouseY, int Button) {
        if (onButton(MouseX, MouseY) && Button == 0) {
            module.toggle();
            Client.onEvent(new EventNotifier(module.name, module.toggled));
            Client.log.info(module.name);
        }
    }

    public boolean onButton(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

}