package com.teddyhack.api.config;

import com.teddyhack.client.Teddyhack;
import com.teddyhack.client.ui.clickgui.ClickGUIConfig;

import java.io.IOException;

public class ClickGUILoad {

    public ClickGUILoad() {
        try {
            clickGUILoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String fileName = "teddyhack/";
    String location = "ClickGUI/";

    public void clickGUILoad() throws IOException {
        loadClickGUIPositions();
    }

    public void loadClickGUIPositions() throws IOException {
        Teddyhack.instance.clickGUIScreen.gui.loadConfig(new ClickGUIConfig(fileName + location));
    }
}
