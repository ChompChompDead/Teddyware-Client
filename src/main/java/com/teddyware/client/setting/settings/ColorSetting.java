package com.teddyware.client.setting.settings;

import com.teddyware.client.module.Module;
import com.teddyware.client.setting.Setting;
import com.teddyware.api.util.color.JColor;

import java.awt.*;

public class ColorSetting extends Setting implements com.lukflug.panelstudio.settings.ColorSetting {
    private boolean rainbow;
    private JColor value;

    public ColorSetting (String name, Module parent, final JColor value) {
        this.name = name;
        this.parent = parent;
        this.value = value;
    }

    public JColor getValue() {
        if (rainbow) {
            float[] tickColor = {
                    (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
            };
            int colorRgbO = JColor.HSBtoRGB(tickColor[0], 1, 1);
            return new JColor((colorRgbO >> 16) & 0xFF, (colorRgbO >> 8) & 0xFF, colorRgbO & 0xFF);
        }
        return this.value;
    }

    public static int rainbow(int delay) {
        float[] tickColor = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };
        int colorRgbO = Color.HSBtoRGB(tickColor[0], 1, 1);
        return new Color((colorRgbO >> 16) & 0xFF, (colorRgbO >> 8) & 0xFF, colorRgbO & 0xFF).getRGB();
    }

    public void setValue (boolean rainbow, final JColor value) {
        this.rainbow = rainbow;
        this.value = value;
    }

    public long toInteger() {
        return this.value.getRGB() & (0xFFFFFFFF);
    }

    public void fromInteger (long number) {
        this.value = new JColor(Math.toIntExact(number & 0xFFFFFFFF),true);
    }

    public JColor getColor() {
        return this.value;
    }

    @Override
    public boolean getRainbow() {
        return this.rainbow;
    }

    @Override
    public void setValue(Color value) {
        setValue(getRainbow(),new JColor(value));
    }

    @Override
    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }
}
