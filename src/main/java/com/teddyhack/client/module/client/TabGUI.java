package com.teddyhack.client.module.client;

import com.teddyhack.api.util.font.FontUtil;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventKey;
import com.teddyhack.api.event.events.EventNotifier;
import com.teddyhack.api.event.events.EventRenderGUI;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import com.teddyhack.client.setting.Setting;
import com.teddyhack.client.setting.settings.BooleanSetting;
import com.teddyhack.client.setting.settings.KeybindSetting;
import com.teddyhack.api.util.gui.JColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

public class TabGUI extends Module {

    public static int currentTab;
    public boolean expanded;

    public TabGUI() {
        super("TabGUI", "clickgui but worse", Keyboard.KEY_NONE, Category.Client);
        this.toggled = true;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRenderGUI) {
            // Main tab
            FontRenderer fr = mc.fontRenderer;
            int count = 0;

            // background
            Gui.drawRect(5, 37, 75, 36 + Category.values().length * 16 + 2, new Color(0,0,0,100).getRGB());
            // outline
            Gui.drawRect(7, 40 + currentTab * 16, 7 + 61, 40 + currentTab * 16 + 12, 0xff783F04);

            for (Category c : Category.values()) {
                if (ModuleManager.getModule("CustomFont").isToggled())
                    FontUtil.drawStringWithShadow(true, c.name, 11, 42 + count * 16, new JColor(255,255,255));
                else
                    FontUtil.drawStringWithShadow(false, c.name, 11, 42 + count * 16, new JColor(255,255,255));

                count++;
            }
            if (expanded) {
                // Multiple tabs
                Category category = Category.values()[currentTab];
                List<Module> modules = ModuleManager.getModulesByCategory(category);
                Module moduleOnSetting = modules.get(category.moduleIndex);

                if (modules.size() == 0)
                    return;
                //background
                Gui.drawRect(75, (int) 37.5, 78 + 68, 36 + modules.size() * 16 + 2, new Color(0,0,0,100).getRGB());
                //outline
                Gui.drawRect(75, 40 + category.moduleIndex * 16, 10 + 60 + 68, 40 + category.moduleIndex * 16 + 12, 0xff783F04);

                count = 0;
                for (Module m : modules) {

                    if (count == category.moduleIndex && m.settingExpanded) {

                        if (!m.settings.isEmpty()) {
                            //background
                            Gui.drawRect(146, (int) 37.5, 217, 36 + m.settings.size() * 16 + 2, new Color(0,0,0,100).getRGB());
                            //outline
                            Gui.drawRect(147, 40 + m.settingIndex * 16, 208, 40 + m.settingIndex * 16 + 12, 0xff783F04);
                        }

                        int settingIndex = 0;
                        for (Setting setting : m.settings) {
                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                fr.drawStringWithShadow(setting.name + ": " + (bool.isEnabled() ? "On" : "Off"), 83, 42 + settingIndex * 16, -1);
                            }
                            if (setting instanceof KeybindSetting) {
                                KeybindSetting keyBind = (KeybindSetting) setting;
                                fr.drawStringWithShadow(setting.name + ": " + Keyboard.getKeyName(keyBind.code), 83, 42 + settingIndex * 16, -1);

                            }

                            settingIndex++;
                        }

                    }
                    // names
                    fr.drawStringWithShadow(m.name, 79, 42 + count * 16, -1);
                    count++;
                }
            }
        }
        // make tabgui work lol
        if (e instanceof EventKey) {
            int code = ((EventKey) e).code;
            Category category = Category.values()[currentTab];
            List<Module> modules = ModuleManager.getModulesByCategory(category);
            Module module = modules.get(category.moduleIndex);

            if (code == Keyboard.KEY_UP) {
                if (expanded) {
                    if (category.moduleIndex <= 0) {
                        category.moduleIndex = modules.size() - 1;
                    } else {
                        category.moduleIndex--;
                    }

                    if(module.settingIndex <= 0) {
                        module.settingIndex = module.settings.size() - 1;
                    } else {
                        module.settingIndex--;
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

                    if (module.settingIndex >= module.settings.size() - 1) {
                        module.settingIndex = 0;
                    } else {
                        module.settingIndex++;
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
                    Setting setting = module.settings.get(module.settingIndex);
                    if (setting instanceof BooleanSetting) {
                        if (module.settings.get(module.settingIndex).focused) {
                            ((BooleanSetting)setting).toggle();
                        }
                    }
                } else if (modules.size() == 0) {
                    expanded = false;
                } else if (!expanded) {
                    expanded = true;
                } else if (module.settings.size() == 0){
                    module.settingExpanded = false;
                } else if (!module.settingExpanded) {
                    module.settingExpanded = true;
                }
            }
            if (code == Keyboard.KEY_LEFT) {
                if (module.settingExpanded) {
                    module.settingExpanded = false;
                }
                expanded = false;
            }

            if (code == Keyboard.KEY_RETURN) {
                if (expanded && modules.size() > 0) {
                    if (!module.name.equals("TabGUI")) {
                        module.toggle();
                        Teddyhack.onEvent(new EventNotifier(module.name, module.toggled));
                    }
                }
            }
        }
    }

}
