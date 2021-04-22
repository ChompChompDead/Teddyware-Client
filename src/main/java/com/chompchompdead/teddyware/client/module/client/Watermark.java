package com.chompchompdead.teddyware.client.module.client;

import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Watermark", description = "Shows the client name and version on a GUI.", key = Keyboard.KEY_NONE, category = Category.Client)
public class Watermark extends Module {

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

}
