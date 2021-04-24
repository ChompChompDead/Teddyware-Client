package com.chompchompdead.teddyware.client.module.hud;

import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import com.chompchompdead.teddyware.client.setting.settings.ColorSetting;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import com.chompchompdead.teddyware.client.setting.settings.ModeSetting;
import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Module.Data(name = "ArrayList", description = "Shows which modules are toggled on a GUI.", key = Keyboard.KEY_NONE, category = Category.Hud)
public class ArrayListt extends HUDModule {

    public static Module ArrayListt = null;

    public static BooleanSetting modeInfo = new BooleanSetting("ModeInfo", ArrayListt, false);
    public static BooleanSetting multiRainbow = new BooleanSetting("multiRainbow", ArrayListt, true);
    public static ColorSetting color = new ColorSetting("Color", ArrayListt, new JColor(255, 255, 255));
    public static ModeSetting sortVertical = new ModeSetting("Vertical", ArrayListt, "Up", "Down", "Up");
    public static ModeSetting sortHorizontal = new ModeSetting("Horizontal", ArrayListt, "Right", "Right", "Left");


    ArrayListText arrayListText = new ArrayListText();

    public ArrayListt() {
        super("ArrayList", new Point(127, 57));
        this.addSetting(modeInfo, multiRainbow, color, sortVertical, sortHorizontal);
    }

    @Override
    public String getArrayListInfo() {
        return sortVertical.getMode() + ", " + sortHorizontal.getMode();
    }

    @Override
    public void populate(Theme theme) {
        component = new ListComponent(getName(), theme.getPanelRenderer(), position, arrayListText);
    }

    @Override
    public void onRender() {
        arrayListText.activeModules.clear();
        for (Module module : ModuleManager.getModuleList()) {
            if (module.isToggled() && module.getHidden()) {
                arrayListText.activeModules.add(module);
            }
        }
        arrayListText.activeModules.sort(Comparator.comparing(module ->
            - Teddyware.instance.clickGUIScreen.guiInterface.getFontWidth(module.getName() + " " + module.getArrayListInf())
        ));
    }

    private class ArrayListText implements HUDList {

        public List<Module> activeModules = new ArrayList<>();

        @Override
        public int getSize() {
            return activeModules.size();
        }

        @Override
        public String getItem(int index) {
            Module module = activeModules.get(index);
            return module.getName() + " " + module.getArrayListInf();
        }

        @Override
        public Color getItemColor(int index) {
            return multiRainbow.isEnabled() ?
            new JColor(rainbow(index * 175)) :
            new JColor(color.getValue());
        }

        @Override
        public boolean sortUp() {
            return sortVertical.is("Down");
        }

        @Override
        public boolean sortRight() {
            return sortHorizontal.is("Right");
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
