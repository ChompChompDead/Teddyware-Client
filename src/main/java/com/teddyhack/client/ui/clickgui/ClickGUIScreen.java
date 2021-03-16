package com.teddyhack.client.ui.clickgui;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.mc12.GLInterface;
import com.lukflug.panelstudio.mc12.MinecraftGUI;
import com.lukflug.panelstudio.settings.*;
import com.lukflug.panelstudio.theme.*;
import com.teddyhack.api.util.font.FontUtil;
import com.teddyhack.api.util.gui.ColorModel;
import com.teddyhack.api.util.gui.JColor;
import com.teddyhack.api.util.gui.SyncableColorComponent;
import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import com.teddyhack.client.module.client.ClickGUIModule;
import com.teddyhack.client.setting.Setting;
import com.teddyhack.client.setting.settings.BooleanSetting;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ClickGUIScreen extends MinecraftGUI {

    public static ClickGUIScreen INSTANCE;

    private final Toggleable colorToggle;
    private final GUIInterface guiInterface;
    private final Theme theme;
    public final ClickGUI gui;

    public static final int WIDTH = 100, HEIGHT = 12, DISTANCE = 10;
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
                return "teddyhack:ui/";
            }

            @Override
            public void drawString(Point pos, String s, Color c) {
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GLInterface.end();
                int x = pos.x + 2, y = pos.y + 1;
                if (ModuleManager.getModule("CustomFont").isToggled()) {
                    FontUtil.drawStringWithShadow(true,s,x,y,new JColor(c));
                } else {
                    FontUtil.drawStringWithShadow(false,s,x,y,new JColor(c));
                }
                GLInterface.begin();
            }

            @Override
            public int getFontWidth(String s) {
                if (ModuleManager.getModule("CustomFont").isToggled()) {
                    return Math.round(FontUtil.getStringWidth(true, s)) + 4;
                } else {
                    return Math.round(FontUtil.getStringWidth(false, s)) + 4;
                }
            }

            @Override
            public int getFontHeight() {
                if (ModuleManager.getModule("CustomFont").isToggled()) {
                    return Math.round(FontUtil.getFontHeight(true)) + 2;
                } else {
                    return Math.round(FontUtil.getFontHeight(false)) + 2;
                }
            }
        };
        theme = new CustomTheme(new SettingsColorScheme(
                ClickGUIModule.getInstance().enabledColor,
                ClickGUIModule.getInstance().backgroundColor,
                ClickGUIModule.getInstance().settingBackgroundColor,
                ClickGUIModule.getInstance().outlineColor,
                ClickGUIModule.getInstance().fontColor,
                ClickGUIModule.getInstance().opacity),
                HEIGHT, 2
        );
        gui = new ClickGUI(guiInterface,ClickGUIModule.INSTANCE.description.is("mouse") ? new MouseDescription(new Point(5,0)) : new FixedDescription(new Point(0,0)));
        Point pos = new Point(DISTANCE,DISTANCE);
        for (Category category : Category.values()) {
            DraggableContainer panel = new DraggableContainer(category.name, null, theme.getPanelRenderer(), new SimpleToggleable(false), new SettingsAnimation(ClickGUIModule.INSTANCE.animationSpeed), null, new Point(pos), WIDTH);
            gui.addComponent(panel);
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
                container.addComponent(new SyncableColorComponent(theme, (com.teddyhack.client.setting.settings.ColorSetting) setting, colorToggle, new SettingsAnimation(ClickGUIModule.getInstance().animationSpeed)));
            else if (setting instanceof KeybindSetting)
                container.addComponent(new KeybindComponent(theme.getComponentRenderer(), (KeybindSetting) setting));
        }
    }


    @Override
    protected ClickGUI getGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return 5;
    }

}
