package com.chompchompdead.teddyware.client.gui;

import com.chompchompdead.teddyware.api.event.events.EventRenderGUI;
import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.api.util.font.FontUtil;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import com.chompchompdead.teddyware.client.module.client.ArrayListt;
import com.mojang.realmsclient.gui.ChatFormatting;
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

    public static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module arg0, Module arg1) {
            if (ArrayListt.mode.is("Down")) {
                if (ArrayListt.modeInfo.isEnabled() ? FontUtil.getStringWidth(arg0.getName() + " " + arg0.getArrayListInf()) < FontUtil.getStringWidth(arg1.getName() + " " + arg1.getArrayListInf()) :
                        FontUtil.getStringWidth(arg0.getName()) < FontUtil.getStringWidth(arg1.getName())) {

                    return -1;
                }
                if (ArrayListt.modeInfo.isEnabled() ? FontUtil.getStringWidth(arg0.getName() + " " + arg0.getArrayListInf()) > FontUtil.getStringWidth(arg1.getName() + " " + arg1.getArrayListInf()) :
                        FontUtil.getStringWidth(arg0.getName()) > FontUtil.getStringWidth(arg1.getName())) {
                    return 1;
                }
                return 0;
            } else if (ArrayListt.mode.is("Up"))
                if (ArrayListt.modeInfo.isEnabled() ? FontUtil.getStringWidth(arg0.getName() + " " + arg0.getArrayListInf()) > FontUtil.getStringWidth(arg1.getName() + " " + arg1.getArrayListInf()) :
                        FontUtil.getStringWidth(arg0.getName()) > FontUtil.getStringWidth(arg1.getName())) {
                    return -1;
                }
                if (ArrayListt.modeInfo.isEnabled() ? FontUtil.getStringWidth(arg0.getName() + " " + arg0.getArrayListInf()) < FontUtil.getStringWidth(arg1.getName() + " " + arg1.getArrayListInf()) :
                        FontUtil.getStringWidth(arg0.getName()) < FontUtil.getStringWidth(arg1.getName())) {
                    return 1;
                }
                return 0;
        }
    }

    @SubscribeEvent()
    public void renderOverlay(RenderGameOverlayEvent.Text event) {
        if (ModuleManager.getModule("Hud").toggled) {
            if (mc.world == null || mc.player == null) return;
            Collections.sort(ModuleManager.modules, new ModuleComparator());
            ScaledResolution sr = new ScaledResolution(mc);

            if (ModuleManager.getModule("Watermark").isToggled()) {
                FontUtil.drawStringWithShadow(Teddyware.NAME + " v" + Teddyware.VERSION, 4, 4, new JColor(120, 63, 4));
            }

            if (ModuleManager.getModule("Coords").isToggled()) {
                FontUtil.drawStringWithShadow(
                        ChatFormatting.RESET + "[X] " + ChatFormatting.WHITE + mc.player.getPosition().getX() + " " +
                                ChatFormatting.RESET + "[Y] " + ChatFormatting.WHITE + mc.player.getPosition().getY() + " " +
                                ChatFormatting.RESET + "[Z] " + ChatFormatting.WHITE + mc.player.getPosition().getZ(),
                        4, 26, new JColor(120, 63, 4)
                );
            }

            if (ModuleManager.getModule("ArrayList").isToggled()) {
                final int[] counter = {1};
                int y = 5;

                for (Module mod : ModuleManager.getModuleList()) {
                    if (mod.isToggled() && !mod.hidden.isEnabled()) {
                        if (ArrayListt.modeInfo.isEnabled()) {
                            FontUtil.drawStringWithShadow(
                                    mod.getName() + " " + mod.getArrayListInf(),
                                    sr.getScaledWidth() - FontUtil.getStringWidth(mod.getName() + " " + mod.getArrayListInf()) - 2, y,
                                    ArrayListt.multiRainbow.isEnabled() ?
                                            new JColor(rainbow(counter[0] * 200)) :
                                            new JColor(ArrayListt.color.getValue())
                            );
                        } else {
                            FontUtil.drawStringWithShadow(
                                    mod.getName(),
                                    sr.getScaledWidth() - FontUtil.getStringWidth(mod.getName()) - 2, y,
                                    ArrayListt.multiRainbow.isEnabled() ?
                                    new JColor(rainbow(counter[0] * 200)) :
                                    new JColor(ArrayListt.color.getValue())
                            );
                        }
                        y += FontUtil.getStringHeight(ArrayListt.modeInfo.isEnabled() ? mod.getName() + " " + mod.getArrayListInf() : mod.getName());
                        counter[0]++;
                    }

                    /*
                    ModuleManager.getModuleList().stream()
                        .filter(Module::isToggled)
                        .filter(Module::getHidden)
                        .sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName() + (ArrayListt.moduleMode.isEnabled() ? " " + module.getArrayListInfo() : ""))))
                        .forEach(module -> {
                            FontUtil.drawStringWithShadow(
                                module.getName() + (ArrayListt.moduleMode.isEnabled() ? " " +
                                    ChatFormatting.GRAY + "[" + ChatFormatting.WHITE +
                                    module.getArrayListInfo() + ChatFormatting.GRAY + "]" : ""),
                                    sr.getScaledWidth() - FontUtil.getStringWidth(module.getName() + (ArrayListt.moduleMode.isEnabled() ? " " + module.getArrayListInfo() : "")) - 2,
                                    y + (10 * boost),
                                    ArrayListt.multiRainbow.isEnabled() ?
                                    new JColor(rainbow(counter[0] * 500)) :
                                    new JColor(ArrayListt.color.getColor())
                            );
                            //y += FontUtil.getStringHeight(module.getName());
                            boost++;
                            counter[0]++;
                        });
                    */
                }

            }

            Teddyware.EVENT_BUS.post(new EventRenderGUI());
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
