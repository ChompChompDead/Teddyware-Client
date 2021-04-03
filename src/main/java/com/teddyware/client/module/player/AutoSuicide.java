package com.teddyware.client.module.player;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "AutoSuicide", description = "Does a /kill command in chat.", key = Keyboard.KEY_NONE, category = Category.Player)
public class AutoSuicide extends Module {
    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendChatMessage("/kill");
            toggle();
        }
    }
}