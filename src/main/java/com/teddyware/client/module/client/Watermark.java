package com.teddyware.client.module.client;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Watermark", description = "Shows the client name and version.", key = Keyboard.KEY_NONE, category = Category.Client)
public class Watermark extends Module {

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

}
