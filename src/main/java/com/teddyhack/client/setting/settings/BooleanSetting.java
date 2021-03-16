package com.teddyhack.client.setting.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.setting.Setting;

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
        if (Teddyhack.config != null) {
            Teddyhack.config.save();
        }
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (Teddyhack.config != null) {
            Teddyhack.config.save();
        }
    }

    @Override
    public boolean isOn() {
        return this.isEnabled();
    }
}
