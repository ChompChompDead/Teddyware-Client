package com.chompchompdead.teddyware.client.module.hud;

import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.theme.Theme;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;

import java.awt.*;

/**
 * @author Gamesense and lukflug
 * im sorry for stealing hudeditor. i regret it fully
 */

public abstract class HUDModule extends Module {

    protected FixedComponent component;
    protected Point position;

    public HUDModule(String title, Point defaultPos) {
        super(title, Category.Hud);
        position = defaultPos;
    }
    public abstract void populate(Theme theme);

    public FixedComponent getComponent() {
        return component;
    }

    public void resetPosition() {
        component.setPosition(Teddyware.instance.clickGUIScreen.guiInterface, position);
    }

}
