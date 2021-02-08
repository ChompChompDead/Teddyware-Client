package com.teddyhack.module;

import com.teddyhack.Client;
import com.teddyhack.events.Event;
import net.minecraftforge.common.MinecraftForge;

public class Module {

    public String name, description;
    private int key;
    private Category category;
    public boolean toggled;

    public Module(String name, String description, Category category) {
        super();
        this.name =name;
        this.description =description;
        this.key = 0;
        this.category =category;
        this.toggled =false;
    }

    //protected void onUpdate() { }
    protected void enable() { }
    protected void disable() { }
    public void onEvent(Event e) { }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
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
