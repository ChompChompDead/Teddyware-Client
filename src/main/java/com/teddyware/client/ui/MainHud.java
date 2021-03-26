package com.teddyware.client.ui;

import java.awt.Color;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyware.api.util.font.FontUtil;
import com.teddyware.api.util.color.JColor;
import com.teddyware.client.Teddyware;
import com.teddyware.api.event.events.EventRenderGUI;
import com.teddyware.client.module.Module;
import com.teddyware.client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Collections;
import java.util.Comparator;

public class MainHud extends Gui {

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
        if (ModuleManager.getModule("Hud").toggled) {
            Collections.sort(ModuleManager.modules, new ModuleComparator());
            ScaledResolution sr = new ScaledResolution(mc);

            if (ModuleManager.getModule("Watermark").toggled) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                    FontUtil.drawStringWithShadow(Teddyware.NAME + " v" + Teddyware.VERSION, 4, 4, new JColor(120,63,4));
                }
            }

            if (ModuleManager.getModule("Coords").toggled) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                    FontUtil.drawStringWithShadow(
                            ChatFormatting.RESET + "[X] " + ChatFormatting.WHITE + mc.player.getPosition().getX() + " " +
                                    ChatFormatting.RESET + "[Y] " + ChatFormatting.WHITE + mc.player.getPosition().getY() + " " +
                                    ChatFormatting.RESET + "[Z] " + ChatFormatting.WHITE + mc.player.getPosition().getZ(),
                            4, 26, new JColor(120, 63, 4)
                    );
                }
            }

            if (ModuleManager.getModule("ArrayList").toggled) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                    int y = 5;
                    final int[] counter = {1};
                    for (Module mod : Teddyware.moduleManager.getModuleList()) {
                        if (!mod.getName().equalsIgnoreCase("TabGUI") &&
                                !mod.getName().equalsIgnoreCase("DiscordRPC") &&
                                !mod.getName().equalsIgnoreCase("ClickGUI") &&
                                mod.isToggled()) {
                            FontUtil.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - FontUtil.getStringWidth(mod.getName()) - 2, y, new JColor(rainbow(counter[0] * 500)));
                            y += FontUtil.getFontHeight();
                            counter[0]++;
                        }
                    }
                }
            }

            if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                Teddyware.onEvent(new EventRenderGUI());
            }
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
