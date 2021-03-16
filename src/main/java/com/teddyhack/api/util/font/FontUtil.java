package com.teddyhack.api.util.font;

import com.teddyhack.client.Teddyhack;
import com.teddyhack.api.util.gui.JColor;
import net.minecraft.client.Minecraft;

public class FontUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, JColor color) {
        if(customFont) {
            return Teddyhack.instance.customFontRenderer.drawStringWithShadow(text, x, y, color);
        }
        else {
            return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
        }
    }

    public static int getStringWidth(boolean customFont, String string) {
        if (customFont) {
            return Teddyhack.instance.customFontRenderer.getStringWidth(string);
        }
        else {
            return mc.fontRenderer.getStringWidth(string);
        }
    }

    public static int getFontHeight(boolean customFont) {
        if (customFont) {
            return Teddyhack.instance.customFontRenderer.getHeight();
        }
        else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }

}
