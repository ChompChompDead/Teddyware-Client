package com.teddyware.client.module.client;

import com.teddyware.client.Discord;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

public class DiscordRPC extends Module {

    public DiscordRPC() {
        super("DiscordRPC", "dee cord rich presence :)", Keyboard.KEY_NONE, Category.Client);
        this.toggled = true;
    }

    /*
    public void onEnable() {
        Discord.startRPC();
    }

    public void onDisable() {
        Discord.stopRPC();
    }
     */

}