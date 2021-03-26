package com.teddyware.client.module.client;

import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventUpdate;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.ColorSetting;
import com.teddyware.client.setting.settings.ModeSetting;
import com.teddyware.client.setting.settings.NumberSetting;
import com.teddyware.api.util.color.JColor;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public static ClickGUIModule INSTANCE;

    public ModeSetting description = new ModeSetting("Description", this, "Mouse", "Mouse", "Fixed");
    public ColorSetting enabledColor = new ColorSetting("EnabledColor", this, new JColor(120,63,4, 255));
    public ColorSetting backgroundColor = new ColorSetting("InactiveColor", this, new JColor(0, 0, 0, 255));
    public ColorSetting settingBackgroundColor = new ColorSetting("BackgroundColor", this, new JColor(0, 0, 0, 255));
    public ColorSetting outlineColor = new ColorSetting("SettingsHighlight", this, new JColor(255, 255, 255, 255));
    public ColorSetting fontColor = new ColorSetting("CategoryColor", this, new JColor(120,63,4,255));
    public NumberSetting animationSpeed = new NumberSetting("AnimSpeed", this, 150, 0, 1000, 1);
    public NumberSetting opacity = new NumberSetting("Opacity", this, 255, 0, 255, 1);

    public static ClickGUIModule getInstance() {
        return INSTANCE;
    }

    public ClickGUIModule() {
        super("ClickGUI", "clickgui, finally", Keyboard.KEY_RSHIFT, Category.Client);
        this.addSetting(enabledColor, backgroundColor, settingBackgroundColor, outlineColor, fontColor, animationSpeed, opacity, description);
        INSTANCE = this;
    }

    public void onEnable() {
        Teddyware.instance.clickGUIScreen.enterGUI();
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
