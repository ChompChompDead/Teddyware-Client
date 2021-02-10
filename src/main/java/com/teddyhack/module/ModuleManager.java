package com.teddyhack.module;

import com.teddyhack.Client;
import com.teddyhack.module.client.ChatSuffix;
import com.teddyhack.module.client.FancyChatMessages;
import com.teddyhack.module.movement.Fly;
import com.teddyhack.module.movement.Sprint;
import com.teddyhack.module.render.FullBright;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static ArrayList<Module> modules;

    public ModuleManager() {
        (modules = new ArrayList<Module>()).clear();

        // player
        modules.add(new Sprint());
        modules.add(new Fly());

        // Render
        modules.add(new FullBright());

        // Client
        modules.add(new ChatSuffix());
        modules.add(new FancyChatMessages());
    }

    public Module getModule(String name) {
        for (Module m : this.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public static void onUpdate() {
        modules.stream().filter(Module::isToggled).forEach(Module::onUpdate);
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
