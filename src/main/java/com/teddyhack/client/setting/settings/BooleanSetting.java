package com.teddyhack.client.setting.settings;

import com.teddyhack.client.Client;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.setting.Setting;

public class BooleanSetting extends Setting {
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
        if (Client.config != null) {
            Client.config.save();
        }
    }

    public void toggled() {
        this.enabled = !this.enabled;
        if (Client.config != null) {
            Client.config.save();
        }
    }
}
