package com.teddyware.api.config;

import com.teddyware.client.Teddyware;
import com.teddyware.client.ui.clickgui.ClickGUIConfig;

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
