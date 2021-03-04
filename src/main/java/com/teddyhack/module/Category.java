package com.teddyhack.module;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import static com.teddyhack.module.render.TabGUI.getLongest;

public enum Category {
    COMBAT("Combat"),
    EXPLOITS("Exploits"),
    RENDER("Render"),
    PLAYER("Player"),
    CLIENT("Client"),
    MOVEMENT("Movement");

    public static ArrayList<String> ModuleLS = new ArrayList<String>();

    public String name;
    public int moduleIndex;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static int longestModule = mc.fontRenderer.getStringWidth(getLongest(ModuleLS));
    //public static double sideTabRight = longestModule / 0.5;

    Category(String name) {
        this.name = name;
    }

}
