package com.chompchompdead.teddyware.client.module;

import com.chompchompdead.teddyware.api.event.events.EventRender;
import com.chompchompdead.teddyware.client.module.client.*;
import com.chompchompdead.teddyware.client.module.combat.*;
import com.chompchompdead.teddyware.client.module.exploits.ServerBackdoor;
import com.chompchompdead.teddyware.client.module.exploits.XCarry;
import com.chompchompdead.teddyware.client.module.hud.ArrayListt;
import com.chompchompdead.teddyware.client.module.hud.Coords;
import com.chompchompdead.teddyware.client.module.hud.HUDEditor;
import com.chompchompdead.teddyware.client.module.hud.Watermark;
import com.chompchompdead.teddyware.client.module.movement.*;
import com.chompchompdead.teddyware.client.module.player.*;
import com.chompchompdead.teddyware.client.module.render.*;
import com.chompchompdead.teddyware.api.util.TWTessellator;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.client.Hud;
import com.chompchompdead.teddyware.client.module.hud.TabGUI;
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
        modules.add(new ElytraFly());
        modules.add(new Fly());
        modules.add(new Jesus());
        modules.add(new LongFly());
        modules.add(new NoSlow());
        modules.add(new ReverseStep());
        modules.add(new SafeWalk());
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Strafe());

        // Render
        modules.add(new FullBright());
        modules.add(new HoleESP());
        modules.add(new StorageESP());
        modules.add(new ViewModel());
        modules.add(new Xray());

        // Client
        modules.add(new Hud());
        modules.add(new ChatSuffix());
        modules.add(new ChatFont());
        modules.add(new ChatNotifier());
        modules.add(new ClickGUIModule());
        modules.add(new CustomFont());
        modules.add(new DiscordRPCModule());

        // Hud
        modules.add(new ArrayListt());
        modules.add(new Coords());
        modules.add(new HUDEditor());
        modules.add(new TabGUI());
        modules.add(new Watermark());

        // Player
        modules.add(new AutoSuicide());
        modules.add(new FakePlayer());
        modules.add(new FastUse());
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

    public static void onRender() {
        modules.stream().filter(Module::isToggled).forEach(Module::onRender);
    }

    public static Module getModule(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
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
                    Teddyware.instance.clickGUIScreen.handleKeyEvent(keyCode);
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
        Minecraft.getMinecraft().profiler.startSection(Teddyware.MODID);
        Minecraft.getMinecraft().profiler.startSection("setup");
        TWTessellator.prepare();
        EventRender e = new EventRender(event.getPartialTicks());
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(module -> module.isToggled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        TWTessellator.release();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }
}