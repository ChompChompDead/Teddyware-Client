package com.teddyhack.module;

import com.teddyhack.Client;
import com.teddyhack.module.movement.Fly;
import com.teddyhack.module.movement.Sprint;
import com.teddyhack.module.render.FullBright;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public ArrayList<Module> modules;

    public ModuleManager() {
        (modules = new ArrayList<Module>()).clear();
        // player
        this.modules.add(new Sprint());
        this.modules.add(new Fly());
        this.modules.add(new FullBright());
    }

    public Module getModule(String name) {
        for (Module m : this.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModuleList() {
        return this.modules;
    }

    public static List<Module> getModulesByCategory(Category c) {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : Client.moduleManager.modules) {
            if (m.getCategory() == c)
                modules.add(m);
        }
        return modules;
    }

}
