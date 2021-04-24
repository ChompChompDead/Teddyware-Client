package com.chompchompdead.teddyware.client.module.hud;

import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.ColorSetting;
import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Module.Data(name = "Watermark", description = "Shows the client name and version on a GUI.", key = Keyboard.KEY_NONE, category = Category.Hud)
public class Watermark extends HUDModule {

    public ColorSetting color = new ColorSetting("Color", this, new JColor(120, 63, 4));

    public Watermark() {
        super("Watermark", new Point(122, 57));
        this.addSetting(color);
    }

    @Override
    public void populate(Theme theme) {
        component = new ListComponent(this.getName(), theme.getPanelRenderer(), this.position, new WatermarkText());
    }

    private class WatermarkText implements HUDList {

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public String getItem(int index) {
            return Teddyware.NAME + " v" + Teddyware.VERSION;
        }

        @Override
        public Color getItemColor(int index) {
            return color.getValue();
        }

        @Override
        public boolean sortUp() {
            return false;
        }

        @Override
        public boolean sortRight() {
            return false;
        }
    }
}
