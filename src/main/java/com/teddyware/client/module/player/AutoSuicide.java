package com.teddyware.client.module.player;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

public class AutoSuicide extends Module {

    public AutoSuicide() {
        super("AutoSuicide", "Automatically kills yourself. Useful for regearing in practice servers.", Keyboard.KEY_NONE, Category.Player);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendChatMessage("/kill");
            toggle();
        }
    }
}