package com.chompchompdead.teddyware.client.module.hud;

import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.ColorSetting;
import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Module.Data(name = "Coords", description = "Shows your coordinates", key = Keyboard.KEY_NONE, category = Category.Hud)
public class Coords extends HUDModule {

    public ColorSetting color = new ColorSetting("color", this, new JColor(120, 63, 4));

    public Coords() {
        super("Coords", new Point(122, 57));
        this.addSetting(color);
    }

    @Override
    public void populate(Theme theme) {
        component = new ListComponent(this.getName(), theme.getPanelRenderer(), this.position, new CoordsText());
    }

    private class CoordsText implements HUDList {

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public String getItem(int index) {
            return ChatFormatting.RESET + "[X] " + ChatFormatting.WHITE + mc.player.getPosition().getX() + " " +
                    ChatFormatting.RESET + "[Y] " + ChatFormatting.WHITE + mc.player.getPosition().getY() + " " +
                    ChatFormatting.RESET + "[Z] " + ChatFormatting.WHITE + mc.player.getPosition().getZ();
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
