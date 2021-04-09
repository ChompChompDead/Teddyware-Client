package com.teddyware.api.util.font;

import com.teddyware.api.util.UtilInterface;
import com.teddyware.client.module.ModuleManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FontRenderer implements UtilInterface {

    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile(COLOR_CHAR + "[0123456789abcdefklmnor]");
    private final int[] colorCodes = {0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};
    private final Map<String, Float> cachedStringWidth = new HashMap<>();
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor;
    private final String name;
    private final float size;

    public FontRenderer(String fontName, float fontSize) {
        name = fontName;
        size = fontSize;
        ScaledResolution resolution = new ScaledResolution(mc);

        try {
            prevScaleFactor = resolution.getScaleFactor();
            unicodeFont = new UnicodeFont(getFontByName(fontName).deriveFont(fontSize * prevScaleFactor / 2));
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();

            prevScaleFactor = resolution.getScaleFactor();

            try {
                unicodeFont = new UnicodeFont(getFontByName("Ubuntu").deriveFont(fontSize * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        this.antiAliasingFactor = resolution.getScaleFactor();
    }

    public static Font getFontByName(String name) throws IOException, FontFormatException {
        if (name == "Ubuntu") {
            return getFontFromInput("/assets/teddyware/fonts/Ubuntu.ttf");
        } else if (name == "Lato") {
            return getFontFromInput("/assets/teddyware/fonts/Lato.ttf");
        } else if (name == "Verdana") {
            return getFontFromInput("/assets/teddyware/fonts/Verdana.ttf");
        } else if (name == "Comfortaa") {
            return getFontFromInput("/assets/teddyware/fonts/comfortaa.ttf");
        } else if (name == "Raleway") {
            return getFontFromInput("/assets/teddyware/fonts/Raleway.ttf");
        } else if (name == "ComicSans") {
            return getFontFromInput("/assets/teddyware/fonts/comic-sans.ttf");
        } else {
            return Font.createFont(Font.TRUETYPE_FONT, new File("/assets/ferox/fonts/" + name + ".ttf"));
        }
    }

    private static Font font = null;

    public static Font getFontFromInput(String path) throws IOException, FontFormatException {
        font = Font.createFont(Font.TRUETYPE_FONT, FontRenderer.class.getResourceAsStream(path));

        return font;
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }

        ScaledResolution resolution = new ScaledResolution(mc);

        try {
            if (resolution.getScaleFactor() != prevScaleFactor) {
                prevScaleFactor = resolution.getScaleFactor();
                unicodeFont = new UnicodeFont(getFontByName(name).deriveFont(size * prevScaleFactor / 2));
                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
                unicodeFont.loadGlyphs();
            }
        } catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }

        this.antiAliasingFactor = resolution.getScaleFactor();

        GL11.glPushMatrix();
        GlStateManager.scale(1 / antiAliasingFactor, 1 / antiAliasingFactor, 1 / antiAliasingFactor);

        x *= antiAliasingFactor;
        y *= antiAliasingFactor;

        float originalX = x;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float alpha = (float) (color >> 24 & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);

        int currentColor = color;

        char[] characters = text.toCharArray();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        String[] parts = COLOR_CODE_PATTERN.split(text);

        int index = 0;

        for (String s : parts) {
            for (String s2 : s.split("\n")) {
                for (String s3 : s2.split("\r")) {
                    unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(currentColor));
                    x += unicodeFont.getWidth(s3);

                    index += s3.length();

                    if (index < characters.length && characters[index] == '\r') {
                        x = originalX;

                        index++;
                    }
                }

                if (index < characters.length && characters[index] == '\n') {
                    x = originalX;
                    y += getHeight(s2) * 2;

                    index++;
                }
            }

            if (index < characters.length) {
                char colorCode = characters[index];
                if (colorCode == COLOR_CHAR) {
                    char colorChar = characters[index + 1];
                    int codeIndex = ("0123456789" + "abcdef").indexOf(colorChar);
                    if (codeIndex < 0) {
                        if (colorChar == 'r') {
                            currentColor = color;
                        }
                    } else {
                        currentColor = colorCodes[codeIndex];
                    }
                    index += 2;
                }
            }
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.bindTexture(0);
        GlStateManager.popMatrix();

        return (int) getWidth(text);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        if (text == null || text == "") {
            return;
        }

        float xNew = x - 1.25F, yNew = y - 1.25F;

        drawString(StringUtils.stripControlCodes(text), xNew + 0.5F, yNew + 0.5F, 0x000000);

        drawString(text, xNew, yNew, color);
    }

    public UnicodeFont getFont() {
        return unicodeFont;
    }

    public float getWidth(String text) {
        if (cachedStringWidth.size() > 1000) {
            cachedStringWidth.clear();
        }
        return cachedStringWidth.computeIfAbsent(text, e -> unicodeFont.getWidth(stripColor(text)) / antiAliasingFactor);
    }

    public float getHeight(String s) {
        if (mc.gameSettings.guiScale == 3) {
            return unicodeFont.getHeight(s) / 2.8F;
        }
        return unicodeFont.getHeight(s) / 2.0F;
    }

    public float getStringWidth(String text) {
        if (mc.gameSettings.guiScale == 3) {
            return unicodeFont.getWidth(stripColor(text)) / 2.8F;
        }
        return unicodeFont.getWidth(stripColor(text)) / 2.0F;
    }

    public float getStringHeight(String text) {
        return getHeight(text);
    }

    public static String stripColor(final String input) {
        return input == null ? null : Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-OR]").matcher(input).replaceAll("");
    }

}
