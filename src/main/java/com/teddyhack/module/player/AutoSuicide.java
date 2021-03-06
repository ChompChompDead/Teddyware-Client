package com.teddyhack.module.player;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
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