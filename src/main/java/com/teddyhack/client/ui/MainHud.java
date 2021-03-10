package com.teddyhack.client.ui;

import java.awt.Color;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyhack.client.Teddyhack;
import com.teddyhack.api.event.listeners.EventRenderGUI;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
            FontRenderer fr = mc.fontRenderer;

            if (ModuleManager.getModule("Watermark").toggled) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                    fr.drawStringWithShadow(Teddyhack.NAME + " v" + Teddyhack.VERSION, 4, 4, 0x783F04);
                    fr.drawStringWithShadow("click gui soon", 4, 15, 0x783F04);
                }
            }

            if (ModuleManager.getModule("Coords").toggled) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                    fr.drawStringWithShadow(
                            ChatFormatting.RESET + "[X] " + ChatFormatting.WHITE + mc.player.getPosition().getX() + " " +
                                    ChatFormatting.RESET + "[Y] " + ChatFormatting.WHITE + mc.player.getPosition().getY() + " " +
                                    ChatFormatting.RESET + "[Z] " + ChatFormatting.WHITE + mc.player.getPosition().getZ(),
                            4, 26, 0x783F04
                    );
                }
            }

            if (ModuleManager.getModule("ArrayList").toggled) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                    int y = 4;
                    final int[] counter = {1};
                    for (Module mod : Teddyhack.moduleManager.getModuleList()) {
                        if (!mod.getName().equalsIgnoreCase("TabGUI") &&
                                !mod.getName().equalsIgnoreCase("DiscordRPC") &&
                                !mod.getName().equalsIgnoreCase("ClickGUI") &&
                                mod.isToggled()) {
                            fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 2, y, rainbow(counter[0] * 300));
                            y += fr.FONT_HEIGHT;
                            counter[0]++;
                        }
                    }
                }
            }

            if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                Teddyhack.onEvent(new EventRenderGUI());
            }
        }
    }

    public static int rainbow(int delay){
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
