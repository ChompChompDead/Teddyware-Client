// imports and package name
package com.teddyware.client;

import com.teddyware.api.config.ClickGUILoad;
import com.teddyware.api.config.ClickGUISave;
import com.teddyware.api.config.Config;
import com.teddyware.api.config.ConfigStop;
import com.teddyware.api.event.EventProcessor;
import com.teddyware.api.proxy.CommonProxy;
import com.teddyware.api.util.font.FontUtil;
import com.teddyware.client.command.CommandManager;
import com.teddyware.client.module.ModuleManager;
import com.teddyware.client.setting.SettingManager;
import com.teddyware.client.gui.MainHud;
import com.teddyware.client.gui.clickgui.ClickGUIScreen;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

// set mod variables and client class
@Mod(modid = Teddyware.MODID, name = Teddyware.NAME, version = Teddyware.VERSION)
public class Teddyware {
    public static final String MODID = "teddyware";
    public static final String NAME = "Teddyware";
    public static final String VERSION = "0.29";
    public static final String CLIENT_PROXY_CLASS = "com.teddyware.api.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.teddyware.api.proxy.CommonProxy";
    public static final Logger log = LogManager.getLogger(NAME + " v" + VERSION);
    public static final EventBus EVENT_BUS = new EventManager();

    public static MainHud mainHud;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static Config config;
    public static ClickGUISave clickGUISave;
    public static ClickGUILoad clickGUILoad;
    public static CommandManager commandManager;
    public static ClickGUIScreen clickGUIScreen;
    public static FontUtil fontManager;
    public static EventProcessor eventProcessor;

    @Instance
    public static Teddyware instance = new Teddyware();

    @SidedProxy(clientSide = Teddyware.CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
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

        eventProcessor = new EventProcessor();
        log.info("events are ready");

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

        fontManager = new FontUtil();
        log.info("custom font is ready");

        log.info(NAME + " is done loading!");
        Discord.start();
        Display.setTitle(NAME + " | v" + VERSION);
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }

    public String getName() { return NAME; }
}