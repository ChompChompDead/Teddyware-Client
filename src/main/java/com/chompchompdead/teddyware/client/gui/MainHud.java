package com.chompchompdead.teddyware.client.gui;

import com.chompchompdead.teddyware.api.event.events.EventRenderGUI;
import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.api.util.font.FontUtil;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import com.chompchompdead.teddyware.client.module.hud.ArrayListt;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class MainHud extends Gui {

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent()
    public void renderOverlay(RenderGameOverlayEvent.Text event) {
        if (ModuleManager.getModule("Hud").toggled) {
            if (mc.world == null || mc.player == null) return;

            Teddyware.instance.clickGUIScreen.gui.render();
            ModuleManager.onRender();
            Teddyware.EVENT_BUS.post(new EventRenderGUI());
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
