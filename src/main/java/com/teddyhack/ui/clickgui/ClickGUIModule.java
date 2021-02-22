package com.teddyhack.ui.clickgui;

import com.teddyhack.setting.settings.ColorSetting;
import com.teddyhack.Client;
import com.teddyhack.event.Event;
import com.teddyhack.event.listeners.EventUpdate;
import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.setting.settings.BooleanSetting;
import com.teddyhack.setting.settings.ModeSetting;
import com.teddyhack.setting.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {
    public static ClickGUIModule INSTANCE;

    public ModeSetting theme = new ModeSetting("theme", this, "new", "new", "old");
    public NumberSetting animationSpeed = new NumberSetting("animation", this, 150, 0, 1000, 50);
    public NumberSetting scrolls = new NumberSetting("scrollSpeed", this, 10, 0, 100, 1);
    public ModeSetting scrollMode = new ModeSetting("scroll", this, "container", "container", "screen");
    public ModeSetting description = new ModeSetting("description", this, "mouse", "mouse", "fixed");
    public ColorSetting enabledColor = new ColorSetting("enabledColor", this, new JColor(121, 193, 255, 255)); //(0, 157, 255, 255));
    public ColorSetting backgroundColor = new ColorSetting("bgColor", this, new JColor(0, 0, 0, 255)); //(0, 121, 194, 255));
    public ColorSetting settingBackgroundColor = new ColorSetting("settinBgColor", this, new JColor(0, 0, 0, 255));
    public ColorSetting outlineColor = new ColorSetting("settingsHighlight", this, new JColor(255, 255, 255, 255));
    public ColorSetting fontColor = new ColorSetting("fontColor", this, new JColor(255, 255, 255, 255));
    public NumberSetting opacity = new NumberSetting("opacity", this, 255, 0, 255, 5);

    public BooleanSetting thinGui = new BooleanSetting("thinGui", this, false);

    public ClickGUIModule() {
        super("clickGuiModule", "classic hud", Keyboard.KEY_RSHIFT, Category.CLIENT);
        this.addSetting(thinGui,scrollMode,scrolls,description,animationSpeed,opacity,fontColor,enabledColor,backgroundColor,settingBackgroundColor,outlineColor);
        INSTANCE = this;
    }

    public static Module getClickGuiModule() {
        return INSTANCE;
    }

    public void onEnable() {
        super.onEnable();
        Client.getInstance().clickGUI.enterGUI();
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    this.setToggled(!toggled);
                }
            }
        }
    }
}
