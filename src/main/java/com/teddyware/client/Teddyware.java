// imports and package name
package com.teddyware.client;

import com.teddyware.api.config.ClickGUILoad;
import com.teddyware.api.config.ClickGUISave;
import com.teddyware.api.config.Config;
import com.teddyware.api.config.ConfigStop;
import com.teddyware.api.event.Event;
import com.teddyware.api.util.font.CustomFontRenderer;
import com.teddyware.client.command.CommandManager;
import com.teddyware.client.module.Module;
import com.teddyware.client.module.ModuleManager;
import com.teddyware.api.proxy.CommonProxy;
import com.teddyware.client.module.client.CustomFont;
import com.teddyware.client.setting.SettingManager;
import com.teddyware.client.ui.MainHud;
import com.teddyware.client.ui.clickgui.ClickGUIScreen;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

// set mod variables and client class
@Mod(modid = Teddyware.MODID, name = Teddyware.NAME, version = Teddyware.VERSION)
public class Teddyware
{
    public static final String MODID = "teddyware";
    public static final String NAME = "Teddyware";
    public static final String VERSION = "0.28";
    public static final String CLIENT_PROXY_CLASS = "com.teddyware.api.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.teddyware.api.proxy.CommonProxy";
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

        customFontRenderer = new CustomFontRenderer(new Font(CustomFont.INSTANCE.font.getMode(), Font.PLAIN, 18), false, false);
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

    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        ModuleManager.onWorldRender(event);
    }

    public String getName() { return NAME; }
}