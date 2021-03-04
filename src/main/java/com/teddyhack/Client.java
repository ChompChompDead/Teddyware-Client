// imports and package name
package com.teddyhack;

import com.teddyhack.event.Event;
import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import com.teddyhack.proxy.CommonProxy;
import com.teddyhack.setting.SettingManager;
import com.teddyhack.ui.UIRenderer;
import com.teddyhack.ui.clickgui.ClickGUI;
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

// set mod variables and client class
@Mod(modid = Client.MODID, name = Client.NAME, version = Client.VERSION)
public class Client
{
    public static final String MODID = "teddyhack";
    public static final String NAME = "Teddyhack";
    public static final String VERSION = "0.15";
    public static final String CLIENT_PROXY_CLASS = "com.teddyhack.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.teddyhack.proxy.CommonProxy";
    public static final Logger log = LogManager.getLogger(NAME + " v" + VERSION);

    public static UIRenderer uirenderer;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;

    @Instance
    public static Client instance = new Client();

    public static Client getInstance() {
        return instance;
    }

    @SidedProxy(clientSide = Client.CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) { }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(new UIRenderer());
        MinecraftForge.EVENT_BUS.register(new ModuleManager());
        MinecraftForge.EVENT_BUS.register(new SettingManager());

        // register stuff
        uirenderer = new UIRenderer();
        log.info("ui is ready");
        moduleManager = new ModuleManager();
        log.info("module manager is ready");
        settingManager = new SettingManager();
        log.info("settings are ready");

        log.info(NAME + " is ready!");
        Discord.startRPC();
        Display.setTitle(NAME + " | v" + VERSION);
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) { }

    public static void onEvent(Event e) {
        for (Module m : ModuleManager.modules) {
            if(!m.toggled)
                continue;

            m.onEvent(e);
        }
    }

    public String getName() { return NAME; }
}
