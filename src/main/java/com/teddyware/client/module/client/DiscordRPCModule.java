package com.teddyware.client.module.client;

import com.teddyware.client.Discord;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "DiscordRPC", description = "Rich presence for discord that shows teddyware's status.", key = Keyboard.KEY_NONE, category = Category.Client)
public class DiscordRPCModule extends Module {

    public DiscordRPCModule() {
        this.toggled = true;
    }

    public void onEnable() {
        Discord.start();
    }

    public void onDisable() {
        Discord.stop();
    }

}