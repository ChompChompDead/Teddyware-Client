package com.chompchompdead.teddyware.api.util.color;

public class ColorUtil {

    public static int toRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b << 0) + (a << 24);
    }

    public static int toRGBA(float r, float g, float b, float a) {
        return toRGBA((int) (r * 255.f), (int) (g * 255.f), (int) (b * 255.f), (int) (a * 255.f));
    }
    public static int toRGBA(float[] colors) {
        if(colors.length != 4) throw new IllegalArgumentException("colors[] must have a length of 4!");
        return toRGBA(colors[0], colors[1], colors[2], colors[3]);
    }
    public static int toRGBA(double[] colors) {
        if(colors.length != 4) throw new IllegalArgumentException("colors[] must have a length of 4!");
        return toRGBA((float)colors[0], (float)colors[1], (float)colors[2], (float)colors[3]);
    }


    public static class Colors {
        public final static int WHITE           = toRGBA(255,     255,    255,    255);
        public final static int BLACK           = toRGBA(0,       0,      0,      255);
        public final static int RED             = toRGBA(255,     0,      0,      255);
        public final static int GREEN           = toRGBA(0,       255,    0,      255);
        public final static int BLUE            = toRGBA(0,       0,      255,    255);
        public final static int ORANGE          = toRGBA(255,     128,    0,      255);
        public final static int PURPLE          = toRGBA(163,     73,     163,    255);
        public final static int GRAY            = toRGBA(127,     127,    127,    255);
        public final static int DARK_RED        = toRGBA(64,      0,      0,      255);
        public final static int YELLOW          = toRGBA(255,     255,    0,      255);
        public final static int RAINBOW = Integer.MIN_VALUE;
    }

}
