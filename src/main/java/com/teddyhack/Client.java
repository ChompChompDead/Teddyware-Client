// imports and package name
package com.teddyhack;

import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import com.teddyhack.ui.UIRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

// set mod variables and client class
@Mod(modid = Client.MODID, name = Client.NAME, version = Client.VERSION)
public class Client
{
    public static final String MODID = "teddyhack";
    public static final String NAME = "Teddyhack";
    public static final String VERSION = "0.1";

    public static UIRenderer uirenderer;
    public static ModuleManager moduleManager;
    private static Logger logger;

    @Instance
    public Client instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        MinecraftForge.EVENT_BUS.register(new UIRenderer());

        System.out.println(NAME + " is ready!");
        Display.setTitle(NAME + " | v" + VERSION);

        // register modules
        uirenderer = new UIRenderer();
        moduleManager = new ModuleManager();
    }

    @EventHandler
    public void PostInit(FMLPreInitializationEvent event) {

    }

    @SubscribeEvent
    public void key(KeyInputEvent e) {
        if(Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null)
            return;
    try {
        if (Keyboard.isCreated()) {
            if (Keyboard.getEventKeyState()) {
                int keyCode = Keyboard.getEventKey();
                if (keyCode <= 0)
                    return;
                for (Module m : moduleManager.modules) {
                    if (m.getKey() == keyCode && keyCode > 0) {
                        m.toggle();
                    }
                }
            }
        }
        } catch (Exception q) {
            q.printStackTrace();
        }
    }
}
