// imports and package name
package com.teddyhack;

import com.teddyhack.event.Event;
import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import com.teddyhack.proxy.CommonProxy;
import com.teddyhack.rpc.Discord;
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
import org.lwjgl.opengl.Display;

// set mod variables and client class
@Mod(modid = Client.MODID, name = Client.NAME, version = Client.VERSION)
public class Client
{
    public static final String MODID = "teddyhack";
    public static final String NAME = "Teddyhack";
    public static final String VERSION = "0.1";
    public static final String CLIENT_PROXY_CLASS = "com.teddyhack.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.teddyhack.proxy.CommonProxy";

    public static UIRenderer uirenderer;
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public ClickGUI clickGUI;

    @Instance
    public static Client instance;

    public static Client getInstance() {
        return instance;
    }

    @SidedProxy(clientSide = Client.CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(new UIRenderer());
        MinecraftForge.EVENT_BUS.register(new ModuleManager());
        MinecraftForge.EVENT_BUS.register(new SettingManager());

        System.out.println(NAME + " is ready!");
        Display.setTitle(NAME + " | v" + VERSION);
        Discord.startRPC();

        // register stuff
        uirenderer = new UIRenderer();
        moduleManager = new ModuleManager();
        settingManager = new SettingManager();
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
}