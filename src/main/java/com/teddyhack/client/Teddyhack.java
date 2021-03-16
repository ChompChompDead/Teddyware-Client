// imports and package name
package com.teddyhack.client;

import com.teddyhack.api.config.ClickGUILoad;
import com.teddyhack.api.config.ClickGUISave;
import com.teddyhack.api.config.Config;
import com.teddyhack.api.config.ConfigStop;
import com.teddyhack.api.event.Event;
import com.teddyhack.api.util.font.CustomFontRenderer;
import com.teddyhack.client.command.CommandManager;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import com.teddyhack.api.proxy.CommonProxy;
import com.teddyhack.client.setting.SettingManager;
import com.teddyhack.client.ui.MainHud;
import com.teddyhack.client.ui.clickgui.ClickGUIScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

// set mod variables and client class
@Mod(modid = Teddyhack.MODID, name = Teddyhack.NAME, version = Teddyhack.VERSION)
public class Teddyhack
{
    public static final String MODID = "teddyhack";
    public static final String NAME = "Teddyhack";
    public static final String VERSION = "0.27";
    public static final String CLIENT_PROXY_CLASS = "com.teddyhack.api.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.teddyhack.api.proxy.CommonProxy";
    public static final Logger log = LogManager.getLogger(NAME + " v" + VERSION);

    public static MainHud mainHud;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static Config config;
    public static ClickGUISave clickGUISave;
    public static ClickGUILoad clickGUILoad;
    public static CommandManager commandManager;
    public static ClickGUIScreen clickGUIScreen;
    public static CustomFontRenderer customFontRenderer;

    @Instance
    public static Teddyhack instance = new Teddyhack();

    @SidedProxy(clientSide = Teddyhack.CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) { }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(this);

        // register stuff
        MinecraftForge.EVENT_BUS.register(new MainHud());
        mainHud = new MainHud();
        log.info("ui is ready");

        MinecraftForge.EVENT_BUS.register(new ModuleManager());
        moduleManager = new ModuleManager();
        log.info("module manager is ready");

        settingManager = new SettingManager();
        log.info("settings are ready");

        commandManager = new CommandManager();
        log.info("commands are ready");

        config = new Config();
        log.info("configs are ready");

        clickGUIScreen = new ClickGUIScreen();
        log.info("clickgui is ready");

        clickGUISave = new ClickGUISave();
        clickGUILoad = new ClickGUILoad();
        Runtime.getRuntime().addShutdownHook(new ConfigStop());
        log.info("clickGUI saves and loads are ready");

        customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 18), false, false);
        log.info("custom font is ready");

        log.info(NAME + " is done loading!");
        //Discord.startRPC();
        Display.setTitle(NAME + " | v" + VERSION);
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }

    public static void onEvent(Event e) {
        for (Module m : ModuleManager.modules) {
            if(!m.toggled)
                continue;
            m.onEvent(e);
        }
    }

    public static void onRender() {
        for (Module m : moduleManager.getModuleList()) {
            if (!m.toggled)
                continue;
            m.onRender();
        }
    }

    public String getName() { return NAME; }
}