package com.teddyware.client.module.client;

import com.teddyware.client.Discord;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

public class DiscordRPCModule extends Module {

    public DiscordRPCModule() {
        super("DiscordRPC", "dee cord rich presence :)", Keyboard.KEY_NONE, Category.Client);
        this.toggled = true;
    }

    public void onEnable() {
        Discord.start();
    }

    public void onDisable() {
        Discord.stop();
    }

}