package com.chompchompdead.teddyware.client.module;

import com.chompchompdead.teddyware.api.event.events.EventNotifier;
import com.chompchompdead.teddyware.api.event.events.EventRender;
import com.chompchompdead.teddyware.client.module.client.ArrayListt;
import com.lukflug.panelstudio.settings.Toggleable;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.chompchompdead.teddyware.client.Teddyware;
import com.chompchompdead.teddyware.client.setting.Setting;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import com.chompchompdead.teddyware.client.setting.settings.KeybindSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// i am cool

public abstract class Module implements Toggleable {

    public String name, description;
    public int key;
    public Category category;
    public boolean toggled;
    public boolean moduleOpen;
    public int settingIndex;
    public boolean settingExpanded;
    public String arrayListInfo;

    public KeybindSetting keyCode = new KeybindSetting(0);
    public BooleanSetting hidden = new BooleanSetting("Hidden", this, false);
    public List<Setting> settings = new ArrayList<>();

    public Minecraft mc = Minecraft.getMinecraft();

    public Module() {
        super();
        this.name = getAnnotation().name();
        this.description = getAnnotation().description();
        keyCode.code = getAnnotation().key();
        this.addSetting(keyCode, hidden);
        this.category = getAnnotation().category();
        this.toggled = false;
    }

    // made purely for hudmodule :)
    public Module(String name, Category category) {
        super();
        this.name = name;
        this.description = null;
        keyCode.code = getAnnotation().key();
        this.addSetting(keyCode, hidden);
        this.category = category;
        this.toggled = false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Data {
        String name();
        String description() default "";
        int key();
        Category category();
    }

    private Data getAnnotation() {
        if (getClass().isAnnotationPresent(Data.class)) {
            return getClass().getAnnotation(Data.class);
        }
        throw new IllegalStateException("No data annotation on module " + this.getClass().getCanonicalName() + ".");
    }

    public void onWorldRender(EventRender e) { }
    public void onUpdate() { }

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
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
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

    public String getArrayListInfo() {
        return "";
    }

    public String getArrayListInf() {
        if (getArrayListInfo() == "") {
            return "";
        }
        return ArrayListt.modeInfo.isEnabled() ? ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + getArrayListInfo() + ChatFormatting.GRAY + "]" : "";
    }

    public boolean getHidden() {
        return !hidden.isEnabled();
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