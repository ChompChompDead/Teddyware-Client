package com.teddyware.client.module;

import com.teddyware.api.event.events.EventRender;
import com.teddyware.api.util.TWTessellator;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.client.*;
import com.teddyware.client.module.combat.*;
import com.teddyware.client.module.exploits.ServerBackdoor;
import com.teddyware.client.module.exploits.XCarry;
import com.teddyware.client.module.movement.*;
import com.teddyware.client.module.player.AutoSuicide;
import com.teddyware.client.module.player.FakePlayer;
import com.teddyware.client.module.player.NoFall;
import com.teddyware.client.module.player.Velocity;
import com.teddyware.client.module.render.FullBright;
import com.teddyware.client.module.client.Hud;
import com.teddyware.client.module.client.TabGUI;
import com.teddyware.client.module.render.HoleESP;
import com.teddyware.client.module.render.StorageESP;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public static CopyOnWriteArrayList<Module> modules;

    public ModuleManager() {
        modules = new CopyOnWriteArrayList<>();

        // Movement
        modules.add(new Fly());
        modules.add(new Jesus());
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Strafe());

        // Render
        modules.add(new FullBright());
        modules.add(new HoleESP());
        modules.add(new StorageESP());

        // Client
        modules.add(new ArrayListt());
        modules.add(new Hud());
        modules.add(new ChatSuffix());
        modules.add(new ChatFont());
        modules.add(new ChatNotifier());
        modules.add(new ClickGUIModule());
        modules.add(new Coords());
        modules.add(new CustomFont());
        modules.add(new TabGUI());
        modules.add(new Watermark());
        modules.add(new DiscordRPC());

        // Player
        modules.add(new AutoSuicide());
        modules.add(new FakePlayer());
        modules.add(new NoFall());
        modules.add(new Velocity());

        // Exploits
        modules.add(new ServerBackdoor());
        modules.add(new XCarry());

        // Combat
        modules.add(new AutoArmor());
        modules.add(new AutoCrystal());
        modules.add(new AutoTotem());
        modules.add(new BowSpam());
        modules.add(new Burrow());
        modules.add(new KillAura());
        modules.add(new Offhand());
    }

    public static void onUpdate() {
        modules.stream().filter(Module::isToggled).forEach(Module::onUpdate);
    }

    public static Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public static CopyOnWriteArrayList<Module> getModuleList() {
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
                    for(Module m : ModuleManager.modules) {
                        if(m.keyCode.getKey() == keyCode && keyCode > 0) {
                            m.toggle();
                        }
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }

    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().mcProfiler.startSection(Teddyware.MODID);
        Minecraft.getMinecraft().mcProfiler.startSection("setup");
        TWTessellator.prepare();
        EventRender e = new EventRender(event.getPartialTicks());
        Minecraft.getMinecraft().mcProfiler.endSection();

        modules.stream().filter(module -> module.isToggled()).forEach(module -> {
            Minecraft.getMinecraft().mcProfiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().mcProfiler.endSection();
        });

        Minecraft.getMinecraft().mcProfiler.startSection("release");
        TWTessellator.release();
        Minecraft.getMinecraft().mcProfiler.endSection();
        Minecraft.getMinecraft().mcProfiler.endSection();
    }
}