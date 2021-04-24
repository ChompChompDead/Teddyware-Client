package com.chompchompdead.teddyware.client.gui.clickgui;

import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.api.util.color.SyncableColorComponent;
import com.chompchompdead.teddyware.api.util.font.FontUtil;
import com.chompchompdead.teddyware.client.module.hud.HUDModule;
import com.lukflug.panelstudio.*;
import com.lukflug.panelstudio.hud.HUDClickGUI;
import com.lukflug.panelstudio.hud.HUDPanel;
import com.lukflug.panelstudio.mc12.GLInterface;
import com.lukflug.panelstudio.mc12.MinecraftHUDGUI;
import com.lukflug.panelstudio.settings.*;
import com.lukflug.panelstudio.theme.*;
import com.chompchompdead.teddyware.api.util.color.ColorModel;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import com.chompchompdead.teddyware.client.module.client.ClickGUIModule;
import com.chompchompdead.teddyware.client.setting.Setting;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;

import java.awt.*;

public class ClickGUIScreen extends MinecraftHUDGUI {

    public static ClickGUIScreen INSTANCE;

    private final Toggleable colorToggle;
    public final GUIInterface guiInterface;
    private final Theme theme;
    public final HUDClickGUI gui;

    public static final int WIDTH = 100, HEIGHT = 12, DISTANCE = 10, HUDBORDER = 2;
    private Category category;
    private Module module;

    public ClickGUIScreen() {
        INSTANCE = this;

        colorToggle = new Toggleable() {
            @Override
            public void toggle() {
                ColorModel.colorModel.increment();
            }

            @Override
            public boolean isOn() {
                return ColorModel.colorModel.is("RGB");
            }
        };
        guiInterface = new GUIInterface(true) {
            @Override
            protected String getResourcePrefix() {
                return "teddyware:ui/";
            }

            @Override
            public void drawString(Point pos, String s, Color c) {
                GLInterface.end();
                int x = pos.x + 2, y = pos.y + 1;
                FontUtil.drawStringWithShadow(s,x,y,new JColor(c));
                GLInterface.begin();
            }

            @Override
            public int getFontWidth(String s) {
                return Math.round(FontUtil.getStringWidth(s)) + 2;
            }

            @Override
            public int getFontHeight() {
                return Math.round(11) + 2;
            }
        };
        theme = new CustomTheme(new SettingsColorScheme(
                ClickGUIModule.getInstance().enabledColor,
                ClickGUIModule.getInstance().backgroundColor,
                ClickGUIModule.getInstance().settingBackgroundColor,
                ClickGUIModule.getInstance().outlineColor,
                ClickGUIModule.getInstance().fontColor,
                ClickGUIModule.getInstance().opacity),
                HEIGHT, 1
        );

        gui = new HUDClickGUI(guiInterface,ClickGUIModule.INSTANCE.description.is("Mouse") ? new MouseDescription(new Point(5,0)) : new FixedDescription(new Point(0,0))) {
            @Override
            public void handleScroll (int diff) {
                super.handleScroll(diff);
                if (ClickGUIModule.INSTANCE.scrollMode.is("GUI")) {
                    for (FixedComponent component : components) {
                        if (!hudComponents.contains(component)) {
                            Point p = component.getPosition(guiInterface);
                            p.translate(0, -diff);
                            component.setPosition(guiInterface, p);
                        }
                    }
                }
            }
        };

        Toggleable hudToggle = new Toggleable() {
            @Override
            public void toggle() {

            }

            @Override
            public boolean isOn() {
                return gui.isOn() || hudEditor;
            }
        };

        for (Module module : ModuleManager.getModuleList()) {
            if (module instanceof HUDModule) {
                HUDModule hudModule = ((HUDModule) module);
                hudModule.populate(theme);
                gui.addHUDComponent(new HUDPanel(hudModule.getComponent(), theme.getPanelRenderer(), module, new SettingsAnimation(ClickGUIModule.INSTANCE.animationSpeed), hudToggle, HUDBORDER));
            }
        }

        Point pos = new Point(DISTANCE,DISTANCE);
        for (Category category : Category.values()) {
            DraggableContainer panel = new DraggableContainer(category.name, null, theme.getPanelRenderer(), new SimpleToggleable(false), new SettingsAnimation(ClickGUIModule.INSTANCE.animationSpeed), null, new Point(pos), WIDTH) {
                @Override
                protected int getScrollHeight (int childHeight) {
                    if (ClickGUIModule.INSTANCE.scrollMode.is("GUI")) return childHeight;
                    return Math.min(childHeight,Math.max(HEIGHT * 4, ClickGUIScreen.this.height - getPosition (guiInterface).y - renderer.getHeight(open.getValue() != 0) - HEIGHT));
                }
            };
            gui.addComponent(panel);
            pos.translate(WIDTH + DISTANCE, 0);
            for (Module module : ModuleManager.getModulesByCategory(category)) {
                addModule(panel, module);
            }
        }
    }

    private void addModule(CollapsibleContainer panel, Module module) {
        CollapsibleContainer container=new CollapsibleContainer(module.getName(),module.getDescription(),theme.getContainerRenderer(),new SimpleToggleable(false),new SettingsAnimation(ClickGUIModule.INSTANCE.animationSpeed), module);
        panel.addComponent(container);
        for (Setting setting : module.settings) {
            if (setting instanceof BooleanSetting)
                container.addComponent(new BooleanComponent(setting.name, null, theme.getComponentRenderer(), (Toggleable) setting));
            else if (setting instanceof NumberSetting)
                container.addComponent(new NumberComponent(setting.name, null, theme.getComponentRenderer(), (NumberSetting) setting, ((NumberSetting) setting).getMinimumValue(), ((NumberSetting) setting).getMaximumValue()));
            else if (setting instanceof EnumSetting)
                container.addComponent(new EnumComponent(setting.name, null, theme.getComponentRenderer(), (EnumSetting) setting));
            else if (setting instanceof ColorSetting)
                container.addComponent(new SyncableColorComponent(theme, (com.chompchompdead.teddyware.client.setting.settings.ColorSetting) setting, colorToggle, new SettingsAnimation(ClickGUIModule.getInstance().animationSpeed)));
            else if (setting instanceof KeybindSetting)
                container.addComponent(new ClickGUIKeybind(theme.getComponentRenderer(), (KeybindSetting) setting));
        }
    }

    @Override
    protected HUDClickGUI getHUDGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return (int) ClickGUIModule.INSTANCE.scrollSpeed.getValue();
    }

}
