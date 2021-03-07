package com.teddyhack.api.config;

import com.teddyhack.client.Client;
import com.teddyhack.client.module.Module;
import com.teddyhack.client.module.ModuleManager;
import com.teddyhack.client.setting.Setting;
import com.teddyhack.client.setting.settings.BooleanSetting;
import com.teddyhack.client.setting.settings.ModeSetting;
import com.teddyhack.client.setting.settings.NumberSetting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

public class Config {

    private final File dir;
    private final File dataFile;

    public Config() {
        dir = new File(Minecraft.getMinecraft().mcDataDir, Client.NAME.toLowerCase());
        if (!dir.exists()) {
            dir.mkdir();
        }
        dataFile = new File(dir, "config.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.load();
    }

    public void save() {
        ArrayList<String> toSave = new ArrayList<String>();

        for (Module mod : Client.instance.moduleManager.modules) {
            toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
        }

        for(Module mod : ModuleManager.modules) {
            for (Setting setting : mod.settings) {
                if (setting instanceof BooleanSetting) {
                    BooleanSetting bool = (BooleanSetting) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + bool.isEnabled());
                }

                if (setting instanceof NumberSetting) {
                    NumberSetting numb = (NumberSetting) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + numb.getValue());
                }

                if (setting instanceof ModeSetting) {
                    ModeSetting mode = (ModeSetting) setting;
                    toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + mode.getMode());
                }
            }
        }

        try {
            PrintWriter pw = new PrintWriter(this.dataFile);
            for (String str : toSave) {
                pw.println(str);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : lines) {
            String[] args = s.split(":");
            if (s.toLowerCase().startsWith("mod:")) {
                Module m = Client.instance.moduleManager.getModule(args[1]);
                if(m != null) {
                    if(Boolean.parseBoolean(args[2])) {
                        m.setToggled(true);
                    }

                    m.setKey(Integer.parseInt(args[3]));
                }
            } else if (s.toLowerCase().startsWith("set:")) {
                Module m = Client.instance.moduleManager.getModule(args[1]);

                if (m != null) {
                    Setting setting = Client.instance.settingManager.getSettingByName(m,args[2]);
                    if(setting != null) {
                        if(setting instanceof BooleanSetting) {
                            ((BooleanSetting)setting).setEnabled(Boolean.parseBoolean(args[3]));
                        }
                        if(setting instanceof NumberSetting) {
                            ((NumberSetting)setting).setValue(Double.parseDouble(args[3]));
                        }
                        if(setting instanceof ModeSetting) {
                            ((ModeSetting)setting).setMode(args[3]);
                        }
                    }
                }
            }
        }
    }
}
