package com.teddyware.client.module.client;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

public class DiscordRPC extends Module {

    public DiscordRPC() {
        super("DiscordRPC", "Show everyone you're playing Teddyware on Discord.", Keyboard.KEY_NONE, Category.Client);
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
