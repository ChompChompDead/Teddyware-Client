package com.teddyhack.client.module.client;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import org.lwjgl.input.Keyboard;

public class DiscordRPC extends Module {

    public DiscordRPC() {
        super("DiscordRPC", "dee cord rich presence :)", Keyboard.KEY_NONE, Category.Client);
        this.toggled = true;
    }

    /*
    @Override
    public void onEnable() {
        Discord.startRPC();
    }

    @Override
    public void onDisable() {
        Discord.stopRPC();
    }
     */
}