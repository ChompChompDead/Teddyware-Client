package com.teddyware.client.module;

import com.lukflug.panelstudio.settings.Toggleable;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.teddyware.api.event.Event;
import com.teddyware.api.event.events.EventNotifier;
import com.teddyware.api.event.events.EventRender;
import com.teddyware.client.Teddyware;
import com.teddyware.client.setting.Setting;
import com.teddyware.client.setting.settings.KeybindSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// i am cool

public class Module implements Toggleable {

    public String name, description;
    public int key;
    private Category category;
    public boolean toggled;
    public boolean moduleOpen;
    public int settingIndex;
    public boolean settingExpanded;

    public KeybindSetting keyCode = new KeybindSetting(0);
    public List<Setting> settings = new ArrayList<Setting>();

    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, int key, Category category) {
        super();
        this.name = name;
        this.description = description;
        keyCode.code = key;
        this.addSetting(keyCode);
        this.category = category;
        this.toggled = false;
    }

    public void onWorldRender(EventRender e) { }
    public void onEvent(Event e) {}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return keyCode.getKey();
    }

    public void setKey(int key) {
        keyCode.setKeyCode(key);
        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

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
        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

    public void toggle() {
        this.toggled = !this.toggled;

        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        Teddyware.EVENT_BUS.subscribe(this);
        Teddyware.EVENT_BUS.post(new EventNotifier.EventNotifierEnable(this));
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        Teddyware.EVENT_BUS.unsubscribe(this);
        Teddyware.EVENT_BUS.post(new EventNotifier.EventNotifierDisable(this));
    }

    public void enable() {
        this.toggled = true;
        onEnable();
    }

    public void disable() {
        this.toggled = false;
        onDisable();
    }

    public boolean isOpen() {
        return moduleOpen;
    }

    public void setOpen(boolean moduleOpen) {
        this.moduleOpen = moduleOpen;
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public static String getToggledStatus(boolean toggle) {
        if (toggle) {
            return ChatFormatting.GREEN + "enabled";
        } else {
            return ChatFormatting.DARK_RED + "disabled";
        }
    }

    public final boolean isOn() {
        return toggled;
    }

}