package com.teddyhack.module;

import com.teddyhack.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Module {

    public String name, description;
    private int key;
    private Category category;
    public boolean toggled;

    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, int key, Category category) {
        super();
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = category;
        this.toggled = false;
    }

    public void onEvent(Event e) {}
    protected void enable() { }
    protected void disable() { }

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
        enable();
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        disable();
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

}
