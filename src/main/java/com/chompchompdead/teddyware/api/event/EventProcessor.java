package com.chompchompdead.teddyware.api.event;

import com.chompchompdead.teddyware.api.event.events.EventRender;
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
        ModuleManager.onWorldRender(event);
        Teddyware.EVENT_BUS.post(new EventRender(event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player != null) {
            ModuleManager.onUpdate();
        }
    }

}
