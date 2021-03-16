package com.teddyhack.api.config;

import com.teddyhack.client.Teddyhack;

import java.io.IOException;

public class ConfigStop extends Thread {

    @Override
    public void run() {
        saveConfig();
    }

    public static void saveConfig() {
        try {
            Teddyhack.instance.clickGUISave.clickGUISave();
            Teddyhack.instance.clickGUISave.saveClickGUIPositions();
            Teddyhack.log.info("ClickGUI positions are saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
