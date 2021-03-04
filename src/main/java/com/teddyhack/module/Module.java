package com.teddyhack.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyhack.event.Event;
import com.teddyhack.setting.Setting;
import com.teddyhack.setting.settings.KeybindSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// i am cool

public class Module {

    public String name, description;
    public int key;
    private Category category;
    public boolean toggled;

    public KeybindSetting keyCode = new KeybindSetting(0);
    public List<Setting> settings = new ArrayList<Setting>();

    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, int key, Category category) {
        super();
        this.name = name;
        this.description = description;
        this.key = key;
        this.addSetting(keyCode);
        this.category = category;
        this.toggled = false;
    }

    public void onEvent(Event e) {}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) { this.key = key; }
    public boolean isToggled() {
        return toggled;
    }

    public void addSetting(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s->s==keyCode?1:0));
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggle() {
        this.toggled = !this.toggled;

        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public static String getToggledStatus(boolean toggle) {
        if (toggle) {
            return ChatFormatting.GREEN + "toggled";
        } else {
            return ChatFormatting.DARK_RED + "not toggled";
        }
    }

}