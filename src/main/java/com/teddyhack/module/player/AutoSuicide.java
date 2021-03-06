package com.teddyhack.module.player;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.module.ModuleManager;
import com.teddyhack.util.ChatUtil;
import org.lwjgl.input.Keyboard;

public class AutoSuicide extends Module {

    public AutoSuicide() {
        super("AutoSuicide", "Automatically kills yourself.", Keyboard.KEY_NONE, Category.Player);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            if (ModuleManager.getModule("ChatSuffix").toggled) {
                ChatUtil.type("You need to disable chatsuffix for AutoSuicide to work!");
                toggle();
            } else {
                mc.player.sendChatMessage("/kill");
                toggle();
            }
        }
    }
}