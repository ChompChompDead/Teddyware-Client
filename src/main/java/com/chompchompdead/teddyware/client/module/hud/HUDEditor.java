package com.chompchompdead.teddyware.client.module.hud;

import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "HUDEditor", description = "An editor for HUD modules.", key = Keyboard.KEY_GRAVE, category = Category.Hud)
public class HUDEditor extends Module {

    @Override
    public void onEnable() {
        Teddyware.clickGUIScreen.enterHUDEditor();
    }

    @Override
    public void onUpdate() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            toggle();
        }
    }
}
