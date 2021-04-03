package com.teddyware.api.util.font;

import java.awt.*;
import java.io.InputStream;

import com.teddyware.api.util.TimerUtil;
import com.teddyware.api.util.UtilInterface;
import com.teddyware.api.util.color.JColor;
import com.teddyware.client.Teddyware;
import com.teddyware.client.module.ModuleManager;
import com.teddyware.client.module.client.CustomFont;

public class FontUtil implements UtilInterface {

    public static FontRender verdana = null;
    public static FontRender lato = null;
    public static FontRender ubuntu = null;
    public static FontRender comfortaa = null;
    public static FontRender comicsans = null;
    public static FontRender raleway = null;
    public static FontRender lexendDeca = null;

    public static TimerUtil cursorTimer = new TimerUtil();
    public static boolean blink = false;

    public void load() {
        try {
            lato = new FontRender(FontUtil.getFont("Lato.ttf", (float) 40.0D));
            comfortaa = new FontRender(FontUtil.getFont("comfortaa.ttf", (float) 40.0D));
            comicsans = new FontRender(FontUtil.getFont("comic-sans.ttf", (float) 40.0D));
            verdana = new FontRender(FontUtil.getFont("Verdana.ttf", (float) 40.0D));
            ubuntu = new FontRender(FontUtil.getFont("Ubuntu.ttf", (float) 40.0D));
            raleway = new FontRender(FontUtil.getFont("Raleway.ttf", (float) 40.0D));
            lexendDeca = new FontRender(FontUtil.getFont("LexendDeca-Regular.tff", (float) 40.0D));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Font getFont(String fontName, float size) {
        try {
            InputStream inputStream = FontUtil.class.getResourceAsStream("/assets/teddyware/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(Font.PLAIN, size);
            inputStream.close();

            return awtClientFont;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("default", Font.PLAIN, (int) size);
        }
    }

    public FontRender getCustomFont() {
        switch (CustomFont.font.getValue()) {
            case 0:
                return lato;
            case 1:
                return raleway;
            case 2:
                return ubuntu;
            case 3:
                return lexendDeca;
            case 4:
                return verdana;
            case 5:
                return comfortaa;
            case 6:
                return comicsans;
        }

        return lato;
    }

    public static void drawString(String text, float x, float y, JColor color) {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            Teddyware.fontManager.getCustomFont().drawString(text, (int) x, (int) y, color.getRGB());
        } else {
            mc.fontRenderer.drawString(text, (int) x, (int) y, color.getRGB());
        }
    }

    public static void drawStringWithShadow(String text, float x, float y, JColor color) {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            Teddyware.fontManager.getCustomFont().drawStringWithShadow(text, (int) x, (int) y, color.getRGB());
        } else {
            mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, color.getRGB());
        }
    }

    public static int getString(String text, float x, float y, JColor color) {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.fontManager.getCustomFont().drawString(text, (int) x, (int) y, color.getRGB());
        } else {
            return mc.fontRenderer.drawString(text, (int) x, (int) y, color.getRGB());
        }
    }

    public static int getString(String text, float x, float y, int color) {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.fontManager.getCustomFont().drawString(text, (int) x, (int) y, color);
        } else {
            return mc.fontRenderer.drawString(text, (int) x, (int) y, color);
        }
    }

    public static int getStringWithShadow(String text, float x, float y, JColor color) {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.fontManager.getCustomFont().drawStringWithShadow(text, (int) x, (int) y, color.getRGB());
        } else {
            return mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, color.getRGB());
        }
    }

    public static float getStringWidth(String text) {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.fontManager.getCustomFont().getStringWidth(text);
        } else {
            return mc.fontRenderer.getStringWidth(text) + 4;
        }
    }

    public static float getFontHeight() {
        if (ModuleManager.getModule("CustomFont").isToggled()) {
            return Teddyware.fontManager.getCustomFont().FONT_HEIGHT;
        } else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }

    public static String insertionPoint() {
        if (cursorTimer.getTimePassed(500, TimerUtil.Format.System)) {
            cursorTimer.reset();
            blink = !blink;
        }

        if (blink) {
            return ModuleManager.getModule("CustomFont").isToggled() ? "|" : "｜";
        } else {
            return "";
        }
    }

}
