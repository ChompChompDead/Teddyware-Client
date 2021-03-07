package com.teddyhack.client.module.player;

import com.teddyhack.client.module.Category;
import com.teddyhack.client.module.Module;
import org.lwjgl.input.Keyboard;

public class AutoSuicide extends Module {

    public AutoSuicide() {
        super("AutoSuicide", "Automatically kills yourself.", Keyboard.KEY_NONE, Category.Player);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendChatMessage("/kill");
            toggle();
        }
    }
}