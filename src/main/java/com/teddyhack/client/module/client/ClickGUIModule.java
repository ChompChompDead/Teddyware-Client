package com.teddyhack.client.module.client;

import com.teddyhack.api.event.Event;
import com.teddyhack.api.event.events.EventUpdate;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.setting.settings.ColorSetting;
import com.teddyhack.client.setting.settings.ModeSetting;
import com.teddyhack.client.setting.settings.NumberSetting;
import com.teddyhack.api.util.gui.JColor;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public static ClickGUIModule INSTANCE;

    public ModeSetting description = new ModeSetting("description", this, "mouse", "mouse", "fixed");
    public ColorSetting enabledColor = new ColorSetting("EnabledColor", this, new JColor(120,63,4, 255));
    public ColorSetting backgroundColor = new ColorSetting("InactiveColor", this, new JColor(0, 0, 0, 255));
    public ColorSetting settingBackgroundColor = new ColorSetting("BackgroundColor", this, new JColor(0, 0, 0, 255));
    public ColorSetting outlineColor = new ColorSetting("SettingsHighlight", this, new JColor(255, 255, 255, 255));
    public ColorSetting fontColor = new ColorSetting("CategoryColor", this, new JColor(120,63,4,255));
    public NumberSetting animationSpeed = new NumberSetting("AnimSpeed", this, 150, 0, 1000, 50);
    public NumberSetting opacity = new NumberSetting("Opacity", this, 255, 0, 255, 5);

    public static ClickGUIModule getInstance() {
        return INSTANCE;
    }

    public ClickGUIModule() {
        super("ClickGUI", "clickgui, finally", Keyboard.KEY_RSHIFT, Category.Client);
        this.addSetting(enabledColor, backgroundColor, settingBackgroundColor, outlineColor, fontColor, animationSpeed, opacity);
        INSTANCE = this;
    }

    public void onEnable() {
        Teddyhack.instance.clickGUIScreen.enterGUI();
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    this.setToggled(!toggled);
                }
            }
        }
    }

    public void onDisable() {

    }
}
