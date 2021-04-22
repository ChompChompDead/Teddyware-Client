package com.chompchompdead.teddyware.api.mixin.mixins;

import com.chompchompdead.teddyware.api.util.font.FontUtil;
import com.chompchompdead.teddyware.client.Teddyware;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = {"drawScreen"}, at = {@At("TAIL")}, cancellable = true)
    public void drawText(CallbackInfo ci) {
        FontUtil.drawStringWithShadow(TextFormatting.GRAY + Teddyware.NAME + " " + TextFormatting.LIGHT_PURPLE + "v" + Teddyware.VERSION, 1, 1, 0xffffff);
    }

}
