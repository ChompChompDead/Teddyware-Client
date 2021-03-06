package com.teddyhack.module.client;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.Discord;
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