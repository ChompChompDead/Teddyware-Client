package com.teddyware.api.util.font2;

import com.teddyware.client.Teddyware;
import com.teddyware.api.util.color.JColor;
import net.minecraft.client.Minecraft;

public class FontUtil2 {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static float drawStringWithShadow(String text, int x, int y, JColor color) {
        if(Teddyware.instance.moduleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.instance.customFontRenderer.drawStringWithShadow(text, x, y, color);

        } else {
            return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
        }
    }

    public static int getStringWidth(String string) {
        if (Teddyware.instance.moduleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.instance.customFontRenderer.getStringWidth(string);
        } else {
            return mc.fontRenderer.getStringWidth(string);
        }
    }

    public static int getFontHeight() {
        if (Teddyware.instance.moduleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.instance.customFontRenderer.getHeight();
        } else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }

}
