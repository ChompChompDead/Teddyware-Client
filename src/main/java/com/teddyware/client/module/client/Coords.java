package com.teddyware.client.module.client;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "Coords", description = "Shows your coordinates on a GUI.", key = Keyboard.KEY_NONE, category = Category.Client)
public class Coords extends Module {

    @Override
    public void onEnable() { }
    @Override
    public void onDisable() { }

}
