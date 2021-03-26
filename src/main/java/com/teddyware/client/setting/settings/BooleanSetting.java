package com.teddyware.client.setting.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.Setting;

public class BooleanSetting extends Setting implements Toggleable {
    public boolean enabled;

    public BooleanSetting(String name, Module parent, boolean enabled) {
        this.name = name;
        this.parent = parent;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    @Override
    public boolean isOn() {
        return this.isEnabled();
    }
}
