// imports and package name
package com.teddyhack.client;

import com.teddyhack.api.config.Config;
import com.teddyhack.api.event.Event;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import com.teddyhack.api.proxy.CommonProxy;
import com.teddyhack.client.setting.SettingManager;
import com.teddyhack.client.ui.MainHud;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

// set mod variables and client class
@Mod(modid = Client.MODID, name = Client.NAME, version = Client.VERSION)
public class Client
{
    public static final String MODID = "teddyhack";
    public static final String NAME = "Teddyhack";
    public static final String VERSION = "0.27";
    public static final String CLIENT_PROXY_CLASS = "com.teddyhack.api.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.teddyhack.api.proxy.CommonProxy";
    public static final Logger log = LogManager.getLogger(NAME + " v" + VERSION);

    public static MainHud uirenderer;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static Config config;

    @Instance
    public static Client instance = new Client();

    @SidedProxy(clientSide = Client.CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) { }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(new MainHud());
        MinecraftForge.EVENT_BUS.register(new ModuleManager());
        MinecraftForge.EVENT_BUS.register(new SettingManager());
        MinecraftForge.EVENT_BUS.register(new Config());

        // register stuff
        uirenderer = new MainHud();
        log.info("ui is ready");
        moduleManager = new ModuleManager();
        log.info("module manager is ready");
        settingManager = new SettingManager();
        log.info("settings are ready");
        config = new Config();
        log.info("configs are ready");

        log.info(NAME + " is ready!");
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

    public String getName() { return NAME; }
}