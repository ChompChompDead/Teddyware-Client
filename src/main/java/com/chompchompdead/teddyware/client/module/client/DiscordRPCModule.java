package com.chompchompdead.teddyware.client.module.client;

import com.chompchompdead.teddyware.client.Discord;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
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