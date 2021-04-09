package com.teddyware.api.config;

import com.google.gson.*;
import com.teddyware.client.Teddyware;
import com.teddyware.client.command.CommandManager;
import com.teddyware.client.module.ModuleManager;
import com.teddyware.client.setting.settings.*;
import net.minecraft.client.Minecraft;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Config {

    public Minecraft mc = Minecraft.getMinecraft();

    public File dir = new File(mc.gameDir, Teddyware.MODID);
    public File moduleDir = new File(dir, "/Modules");
    public File othersDir = new File(dir, "/others.txt");

    public Config() {
        create();

        try {
            this.load();
            this.loadOthers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!moduleDir.exists()) {
            moduleDir.mkdir();
        }
    }

    public void save() {
        create();
        Teddyware.moduleManager.getModuleList().forEach(module -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            OutputStreamWriter fileOutputStreamWriter = null;

            try {
                fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(Teddyware.MODID + "/Modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            JsonObject moduleObj = new JsonObject();
            JsonObject settingObj = new JsonObject();
            moduleObj.add("Module", new JsonPrimitive(module.getName()));
            moduleObj.add("Enabled", new JsonPrimitive(module.isToggled()));

            module.settings.forEach(setting -> {
                if (setting instanceof KeybindSetting) {
                    KeybindSetting key = (KeybindSetting) setting;
                    settingObj.add(setting.name, new JsonPrimitive((key).getKeyCode()));
                }

                if (setting instanceof BooleanSetting) {
                   BooleanSetting bool = (BooleanSetting) setting;
                   settingObj.add(setting.name, new JsonPrimitive((bool).isEnabled()));
                }

                if (setting instanceof NumberSetting) {
                    NumberSetting numb = (NumberSetting) setting;
                    settingObj.add(setting.name, new JsonPrimitive((numb).getValue()));
                }

                if (setting instanceof ModeSetting) {
                    ModeSetting mode = (ModeSetting) setting;
                    settingObj.add(setting.name, new JsonPrimitive((mode).getMode()));
                }

                if (setting instanceof ColorSetting) {
                    ColorSetting color = (ColorSetting) setting;
                    settingObj.add(setting.name + "Rainbow", new JsonPrimitive((color.getRainb())));
                    settingObj.add(setting.name, new JsonPrimitive((color).toInteger()));
                }
            });
            moduleObj.add("Settings", settingObj);

            try {
                String jsonString = gson.toJson(new JsonParser().parse(moduleObj.toString()));
                fileOutputStreamWriter.write(jsonString);
                fileOutputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void load() {
        create();
        if (ModuleManager.modules != null) {
            ModuleManager.getModuleList().forEach(module -> {
                if (!Files.exists(Paths.get(Teddyware.MODID + "/Modules/" + module.getName() + ".json"))) {
                    return;
                }

                InputStream inputStream = null;

                try {
                    inputStream = Files.newInputStream(Paths.get(Teddyware.MODID + "/Modules/" + module.getName() + ".json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JsonObject moduleObj = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
                if (moduleObj.get("Module") == null) return;
                JsonObject settingObj = moduleObj.getAsJsonObject("Settings").getAsJsonObject();

                JsonElement moduleDataObj = moduleObj.get("Enabled");
                if (moduleDataObj != null && moduleDataObj.isJsonPrimitive()) {
                    if (moduleDataObj.getAsBoolean() && !module.name.equals("ClickGUI") && !module.name.equals("FakePlayer")) {
                        module.setToggled(true);
                    }
                }

                module.settings.forEach(setting -> {
                    JsonElement dataObj = settingObj.get(setting.name);

                    try {
                        if (dataObj != null && dataObj.isJsonPrimitive()) {
                            if (setting instanceof KeybindSetting) {
                                KeybindSetting key = (KeybindSetting) setting;
                                key.setKeyCode((int) dataObj.getAsLong());
                            }

                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                bool.setEnabled(dataObj.getAsBoolean());
                            }

                            if (setting instanceof NumberSetting) {
                                NumberSetting numb = (NumberSetting) setting;
                                numb.setValue(dataObj.getAsDouble());
                            }

                            if (setting instanceof ModeSetting) {
                                ModeSetting mode = (ModeSetting) setting;
                                mode.setMode(dataObj.getAsString());
                            }

                            if (setting instanceof ColorSetting) {
                                ColorSetting color = (ColorSetting) setting;
                                JsonElement rainbowDataObj = settingObj.get(color.name + "Rainbow");

                                if (rainbowDataObj.isJsonPrimitive()) {
                                    color.fromInteger(dataObj.getAsInt());
                                    color.setRainb(rainbowDataObj.getAsBoolean());
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        Teddyware.log.info(setting.name + " " + module.getName() + " encountered a setting error.");
                        Teddyware.log.info(dataObj + " was the setting.");
                    }
                });

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    /*
    public void save() {
        create();
        Teddyware.moduleManager.getModuleList().forEach(module -> {
            JSONObject moduleObj = new JSONObject();
            moduleObj.put("enabled", module.isToggled());

            module.settings.forEach(setting -> {
                if (setting instanceof KeybindSetting) {
                    KeybindSetting key = (KeybindSetting) setting;
                    moduleObj.put(setting.name, key.getKeyCode());
                }

                if (setting instanceof BooleanSetting) {
                    BooleanSetting bool = (BooleanSetting) setting;
                    moduleObj.put(setting.name, bool.isEnabled());
                }

                if (setting instanceof NumberSetting) {
                    NumberSetting numb = (NumberSetting) setting;
                    moduleObj.put(setting.name, numb.getValue());
                }

                if (setting instanceof ModeSetting) {
                    ModeSetting mode = (ModeSetting) setting;
                    moduleObj.put(setting.name, mode.getMode());
                }

                if (setting instanceof ColorSetting) {
                    ColorSetting color = (ColorSetting) setting;
                    moduleObj.put(setting.name, color.toInteger());
                }
            });

            try {
                writer = new BufferedWriter(new FileWriter(new File(moduleDir, module.getName() + ".json")));
                writer.write(moduleObj.toJSONString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void load() {
        create();
        Teddyware.moduleManager.getModuleList().forEach(module -> {
            try {
                reader = new BufferedReader(new FileReader(new File(moduleDir, module.getName() + ".json")));
                JSONObject parsedModule = (JSONObject) parser.parse(reader);

                if ((boolean) parsedModule.get("enabled") && !module.name.equals("ClickGUI") && !module.name.equals("FakePlayer")) {
                    module.setToggled(true);
                }

                if (module != null) {
                    module.settings.forEach(setting -> {
                        if (parsedModule.containsKey(setting.name)) {
                            if (setting instanceof KeybindSetting) {
                                Long value = (long) parsedModule.get(setting.name);
                                KeybindSetting key = (KeybindSetting) setting;
                                key.setKeyCode(value.intValue());
                            }

                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                bool.setEnabled((boolean) parsedModule.get(setting.name));
                            }

                            if (setting instanceof NumberSetting) {
                                NumberSetting numb = (NumberSetting) setting;
                                numb.setValue((double) parsedModule.get(setting.name));
                            }

                            if (setting instanceof ModeSetting) {
                                ModeSetting mode = (ModeSetting) setting;
                                mode.setMode((String) parsedModule.get(setting.name));
                            }

                            if (setting instanceof ColorSetting) {
                                ColorSetting color = (ColorSetting) setting;
                                color.fromInteger((long) parsedModule.get(setting.name));
                            }
                        }
                    });
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        });
    }
     */

    public void saveOthers() {
        create();
        ArrayList<String> toSave = new ArrayList<>();

        toSave.add("PREFIX:" + CommandManager.getPrefix());

        try {
            PrintWriter pw = new PrintWriter(othersDir);
            for (String str : toSave) {
                pw.println(str);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadOthers() {
        create();
        ArrayList<String> lines = new ArrayList<>();

        try {
            File file = new File(dir, "others.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
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

            if (s.toLowerCase().startsWith("prefix:")) {
                CommandManager.setPrefix(args[1].charAt(0));
            }
        }
    }
}

