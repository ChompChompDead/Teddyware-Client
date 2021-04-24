package com.chompchompdead.teddyware.client.module.hud;

import com.chompchompdead.teddyware.api.event.events.EventRenderGUI;
import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.api.util.font.FontUtil;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.module.ModuleManager;
import com.chompchompdead.teddyware.client.module.client.ClickGUIModule;
import com.chompchompdead.teddyware.client.module.hud.HUDModule;
import com.chompchompdead.teddyware.client.setting.settings.ColorSetting;
import com.chompchompdead.teddyware.client.setting.Setting;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import com.chompchompdead.teddyware.client.setting.settings.KeybindSetting;
import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.tabgui.DefaultRenderer;
import com.lukflug.panelstudio.tabgui.TabGUIContainer;
import com.lukflug.panelstudio.tabgui.TabGUIItem;
import com.lukflug.panelstudio.tabgui.TabGUIRenderer;
import com.lukflug.panelstudio.theme.SettingsColorScheme;
import com.lukflug.panelstudio.theme.Theme;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

@Module.Data(name = "TabGUI", description = "A GUI similar to ClickGUI for quick use.", key = Keyboard.KEY_NONE, category = Category.Hud)
public class TabGUI extends HUDModule {

    public TabGUI() {
        super("TabGUI", new Point(Teddyware.instance.clickGUIScreen.DISTANCE, Teddyware.instance.clickGUIScreen.DISTANCE));
    }

    @Override
    public void populate(Theme theme) {
        TabGUIRenderer renderer = new DefaultRenderer(new SettingsColorScheme(
                ClickGUIModule.INSTANCE.enabledColor,
                ClickGUIModule.INSTANCE.backgroundColor,
                ClickGUIModule.INSTANCE.settingBackgroundColor,
                ClickGUIModule.INSTANCE.backgroundColor,
                ClickGUIModule.INSTANCE.outlineColor,
                ClickGUIModule.INSTANCE.opacity),
                Teddyware.instance.clickGUIScreen.HEIGHT, 5,
                Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_LEFT,
                Keyboard.KEY_RIGHT, Keyboard.KEY_RETURN
        );

        com.lukflug.panelstudio.tabgui.TabGUI component = new com.lukflug.panelstudio.tabgui.TabGUI("TabGUI", renderer, new Animation() {
            @Override
            protected int getSpeed() {
                return (int) ClickGUIModule.INSTANCE.animationSpeed.getValue();
            }
        }, position, 75);

        for (Category category : Category.values()) {
            TabGUIContainer tab = new TabGUIContainer(category.name(), renderer, new SettingsAnimation(ClickGUIModule.INSTANCE.animationSpeed));
            component.addComponent(tab);
            for (Module module : ModuleManager.getModulesByCategory(category)) {
                tab.addComponent(new TabGUIItem(module.getName(), module));
            }
        }
        this.component = component;
    }
}
