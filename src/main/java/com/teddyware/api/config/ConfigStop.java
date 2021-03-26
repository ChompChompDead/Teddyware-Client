package com.teddyware.api.config;

import com.teddyware.client.Teddyware;

import java.io.IOException;

public class ConfigStop extends Thread {

    @Override
    public void run() {
        saveConfig();
    }

    public static void saveConfig() {
        try {
            Teddyware.instance.clickGUISave.clickGUISave();
            Teddyware.instance.clickGUISave.saveClickGUIPositions();
            Teddyware.instance.config.save();
            Teddyware.instance.config.saveOthers();
            Teddyware.log.info("ClickGUI positions are saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
