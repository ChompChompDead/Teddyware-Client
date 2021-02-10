package com.teddyhack.module;

import com.teddyhack.module.client.ChatSuffix;
import com.teddyhack.module.client.FancyChatMessages;
import com.teddyhack.module.movement.Fly;
import com.teddyhack.module.movement.Sprint;
import com.teddyhack.module.player.NoFall;
import com.teddyhack.module.render.FullBright;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<Module>();

        // player
        modules.add(new Sprint());
        modules.add(new Fly());

        // Render
        modules.add(new FullBright());

        // Client
        modules.add(new ChatSuffix());
        modules.add(new FancyChatMessages());

        // Player
        modules.add(new NoFall());
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModuleList() {
        return modules;
    }

    public static List<Module> getModulesByCategory(Category c) {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : ModuleManager.modules) {
            if (m.getCategory() == c)
                modules.add(m);
        }
        return modules;
    }

}
