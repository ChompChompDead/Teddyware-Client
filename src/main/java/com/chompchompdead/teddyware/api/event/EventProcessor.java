package com.chompchompdead.teddyware.api.event;

import com.chompchompdead.teddyware.api.event.events.EventRender;
import com.chompchompdead.teddyware.api.event.events.EventRenderGUI;
import com.chompchompdead.teddyware.api.util.TWTessellator;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor {

    private Minecraft mc = Minecraft.getMinecraft();

    public EventProcessor() {
        Teddyware.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        mc.profiler.startSection(Teddyware.MODID);
        mc.profiler.startSection("setup");
        TWTessellator.prepare();
        EventRender e = new EventRender(event.getPartialTicks());
        mc.profiler.endSection();

        ModuleManager.getModuleList().stream().filter(module -> module.isToggled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        TWTessellator.release();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();

        Teddyware.EVENT_BUS.post(new EventRender(event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player != null) {
            ModuleManager.onUpdate();
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (mc.world == null || mc.player == null) return;
        Teddyware.instance.clickGUIScreen.gui.render();
        ModuleManager.onRender();
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent key) {
        if(mc.world == null || mc.player == null)
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

}
