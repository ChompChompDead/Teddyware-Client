package com.teddyware.client.module;

import com.teddyware.client.Teddyware;
import com.teddyware.api.event.events.EventKey;
import com.teddyware.api.event.events.EventNotifier;
import com.teddyware.client.module.client.*;
import com.teddyware.client.module.combat.*;
import com.teddyware.client.module.exploits.ServerBackdoor;
import com.teddyware.client.module.movement.*;
import com.teddyware.client.module.player.AutoSuicide;
import com.teddyware.client.module.player.FakePlayer;
import com.teddyware.client.module.player.NoFall;
import com.teddyware.client.module.render.FullBright;
import com.teddyware.client.module.client.Hud;
import com.teddyware.client.module.client.TabGUI;
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
        modules.add(new Fly());
        modules.add(new Jesus());
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Strafe());

        // Render
        modules.add(new FullBright());

        // Client
        modules.add(new com.teddyware.client.module.client.ArrayList());
        modules.add(new Hud());
        modules.add(new ChatSuffix());
        modules.add(new ChatFont());
        modules.add(new ChatNotifier());
        modules.add(new ClickGUIModule());
        modules.add(new Coords());
        modules.add(new CustomFont());
        modules.add(new TabGUI());
        modules.add(new Watermark());

        // Player
        modules.add(new AutoSuicide());
        modules.add(new FakePlayer());
        modules.add(new NoFall());

        // Exploits
        modules.add(new ServerBackdoor());

        // Combat
        modules.add(new AutoArmor());
        modules.add(new AutoTotem());
        modules.add(new BowSpam());
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

    public static ArrayList<Module> getModuleList() {
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
                    Teddyware.onEvent(new EventKey(keyCode));
                    for(Module m : ModuleManager.modules) {
                        if(m.keyCode.getKey() == keyCode && keyCode > 0) {
                            m.toggle();
                            Teddyware.onEvent(new EventNotifier(m.name, m.toggled));
                        }
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }
}