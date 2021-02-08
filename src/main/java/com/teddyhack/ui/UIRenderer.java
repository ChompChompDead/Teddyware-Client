package com.teddyhack.ui;

import java.awt.Color;
import com.teddyhack.Client;
import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Collections;
import java.util.Comparator;

public class UIRenderer extends Gui {

    private final Minecraft mc = Minecraft.getMinecraft();

    public static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module arg0, Module arg1) {
            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
                return -1;
            }
            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
                return 1;
            }
            return 0;
        }
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        Collections.sort(Client.moduleManager.modules, new ModuleComparator());
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRenderer;

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            fr.drawStringWithShadow(Client.NAME + " v" + Client.VERSION, 4, 4, 0x783F04);
            fr.drawStringWithShadow("Press R, G, and C for current modules.", 4, 15, 0x783F04);
        }

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            int y = 4;
            final int[] counter = {1};
            for (Module mod : Client.moduleManager.getModuleList()) {
                if (!mod.getName().equalsIgnoreCase("") && mod.isToggled()) {
                    fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 2, y, rainbow(counter[0] * 300));
                    y += fr.FONT_HEIGHT;
                    counter[0]++;
                }
            }
        }
    }
        public static int rainbow(int delay) {
            double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
            rainbowState %= 360;
            return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();

    }

}



