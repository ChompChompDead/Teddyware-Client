package com.chompchompdead.teddyware.client.setting;

import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.module.ModuleManager;

import java.util.ArrayList;

public class SettingManager {

    private final ArrayList<Setting> settings;

    public SettingManager(){
        this.settings = new ArrayList<Setting>();
    }

    public void rSetting(Setting in){
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        ArrayList<Setting> out = new ArrayList<Setting>();
        for(Setting s : getSettings()) {
            if(s.parent.equals(mod)) {
                out.add(s);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }

    public static Setting getSettingByName(Module mod, String name) {
        for (Module m : ModuleManager.modules) {
            for (Setting set : m.settings) {
                if (set.name.equalsIgnoreCase(name) && set.parent == mod) {
                    return set;
                }
            }
        }
        System.err.println("Teddyhack, Error Setting NOT found: '" + name +"'!");
        return null;
    }
}
