package com.teddyware.client.setting.settings;

import com.lukflug.panelstudio.settings.EnumSetting;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting implements EnumSetting {
    public int index;

    public List<String> modes;

    public ModeSetting(String name, Module parent, String defaultMode, String... modes) {
        this.name = name;
        this.parent = parent;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return this.modes.get(this.index);
    }

    public void setMode(String mode) {
        this.index = this.modes.indexOf(mode);
    }

    public boolean is(String mode) {
        return (this.index == this.modes.indexOf(mode));
    }

    public void increment() {
        if (this.index < this.modes.size() - 1) {
            this.index++;
        } else {
            this.index = 0;
        }
    }

    @Override
    public String getValueName() {
        return this.modes.get(this.index);
    }
}
