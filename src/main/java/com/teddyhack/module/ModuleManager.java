package com.teddyhack.module;

import com.teddyhack.Client;
import com.teddyhack.event.listeners.EventKey;
import com.teddyhack.event.listeners.EventNotifier;
import com.teddyhack.module.client.*;
import com.teddyhack.module.combat.AutoArmor;
import com.teddyhack.module.combat.AutoTotem;
import com.teddyhack.module.combat.KillAura;
import com.teddyhack.module.exploits.ServerBackdoor;
import com.teddyhack.module.movement.Fly;
import com.teddyhack.module.movement.Sprint;
import com.teddyhack.module.movement.Step;
import com.teddyhack.module.player.NoFall;
import com.teddyhack.module.render.FullBright;
import com.teddyhack.module.client.Hud;
import com.teddyhack.module.client.TabGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        // Movement
        modules.add(new Sprint());
        modules.add(new Fly());
        modules.add(new Step());

        // Render
        modules.add(new FullBright());
        modules.add(new Hud());

        // Client
        modules.add(new ChatSuffix());
        modules.add(new ChatFont());
        modules.add(new ChatNotifier());
        modules.add(new ClickGUI());
        modules.add(new Coords());
        modules.add(new Watermark());
        modules.add(new com.teddyhack.module.client.ArrayList());
        modules.add(new TabGUI());

        // Player
        modules.add(new NoFall());

        // Exploits
        modules.add(new ServerBackdoor());

        // Combat
        modules.add(new AutoTotem());
        modules.add(new AutoArmor());
        modules.add(new KillAura());
    }

    public static Module getModule(String name) {
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

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent key) {
        if(Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null)
            return;
        try {
            if(Keyboard.isCreated()) {
                if(Keyboard.getEventKeyState()) {
                    int keyCode = Keyboard.getEventKey();
                    if(keyCode <= 0)
                        return;
                    Client.onEvent(new EventKey(keyCode));
                    for(Module m : ModuleManager.modules) {
                        if(m.getKey() == keyCode && keyCode > 0) {
                            m.toggle();
                            Client.onEvent(new EventNotifier(m.name, m.toggled));
                            //ChatUtil.type(m.name + " is " + Module.getToggledStatus(m.toggled));
                        }
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }
}