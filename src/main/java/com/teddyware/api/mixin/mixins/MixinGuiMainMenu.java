package com.teddyware.api.mixin.mixins;

import com.teddyware.client.Teddyware;
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
        mc.fontRenderer.drawStringWithShadow(TextFormatting.GRAY + "Teddyhack " + TextFormatting.LIGHT_PURPLE + "v" + Teddyware.VERSION, 1, 1, 0xffffff);
    }

}
