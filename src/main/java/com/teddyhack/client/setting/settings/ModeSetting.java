package com.teddyhack.client.setting.settings;

import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.setting.Setting;
import scala.actors.threadpool.Arrays;

import java.util.List;

public class ModeSetting extends Setting {
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
        if (Teddyhack.config != null) {
            Teddyhack.config.save();
        }
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
}
