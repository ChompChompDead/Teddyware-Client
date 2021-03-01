package com.teddyhack.module.render;

import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventKey;
import com.teddyhack.event.listeners.EventRenderGUI;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import com.teddyhack.util.ChatUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class TabGUI extends Module {

    public int currentTab;
    public boolean expanded;

    public TabGUI() {
        super("TabGUI", "clickgui but worse", Keyboard.KEY_NONE, Category.RENDER);
        this.toggled = true;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRenderGUI) {
            // Main tab
            FontRenderer fr = mc.fontRenderer;

            Gui.drawRect(5, 30, 75, 30 + Category.values().length * 16 + 2, 0x90000000);
            Gui.drawRect(7, 33 + currentTab * 16, 7 + 61, 33 + currentTab * 16 + 12, 0xff783F04);

            int count = 0;
            for (Category c : Category.values()) {
                fr.drawStringWithShadow(c.name, 11, 35 + count * 16, -1);

                count++;
            }

            if (expanded) {
                // Multiple tabs
                Category category = Category.values()[currentTab];
                List<Module> modules = ModuleManager.getModulesByCategory(category);

                if (modules.size() == 0)
                    return;

                //background
                Gui.drawRect(75, (int) 30.5, 75 + 68, 30 + modules.size() * 16 + (int) 1.5, 0x90000000);
                //outline
                Gui.drawRect(75, 33 + category.moduleIndex * 16, 10 + 60 + 68, 33 + category.moduleIndex * 16 + 12, 0xff783F04);

                count = 0;
                for (Module m : modules) {
                    // names
                    fr.drawStringWithShadow(m.name, 79, 35 + count * 16, -1);

                    count++;
                }
            }
        }
        // make tabgui work lol
        if (e instanceof EventKey) {
            int code = ((EventKey) e).code;
            Category category = Category.values()[currentTab];
            List<Module> modules = ModuleManager.getModulesByCategory(category);

            if (code == Keyboard.KEY_UP) {
                if (expanded) {
                    if (category.moduleIndex <= 0) {
                        category.moduleIndex = modules.size() - 1;
                    } else {
                        category.moduleIndex--;
                    }
                } else {
                    if (currentTab <= 0) {
                        currentTab = Category.values().length - 1;
                    } else {
                        currentTab--;
                    }
                }
            }

            if (code == Keyboard.KEY_DOWN) {
                if (expanded) {
                    if (category.moduleIndex >= modules.size() - 1) {
                        category.moduleIndex = 0;
                    } else {
                        category.moduleIndex++;
                    }
                } else {
                    if (currentTab >= Category.values().length - 1) {
                        currentTab = 0;
                    } else {
                        currentTab++;
                    }
                }
            }
            if (code == Keyboard.KEY_RIGHT) {
                if (expanded && modules.size() > 0) {
                    Module module = modules.get(category.moduleIndex);
                    if (!module.name.equals("TabGUI")) {
                        module.toggle();
                        ChatUtil.type(module.name + " is now " + Module.getToggledStatus(module.toggled));
                    }
                } else if (modules.size() == 0) {
                    expanded = false;
                } else if (!expanded) {
                    expanded = true;
                }
            }
            if (code == Keyboard.KEY_LEFT) {
                expanded = false;
            }
        }
    }

}
