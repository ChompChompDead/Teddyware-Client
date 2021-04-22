package com.chompchompdead.teddyware.api.config;

import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.gui.clickgui.ClickGUIConfig;

import java.io.IOException;

public class ClickGUILoad {

    public ClickGUILoad() {
        try {
            clickGUILoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String fileName = "teddyware/";
    String location = "ClickGUI/";

    public void clickGUILoad() throws IOException {
        loadClickGUIPositions();
    }

    public void loadClickGUIPositions() throws IOException {
        Teddyware.instance.clickGUIScreen.gui.loadConfig(new ClickGUIConfig(fileName + location));
    }
}
