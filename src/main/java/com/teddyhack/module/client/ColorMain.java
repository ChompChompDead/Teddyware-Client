package com.teddyhack.module.client;

import com.teddyhack.module.Category;
import com.teddyhack.module.Module;
import com.teddyhack.setting.settings.ModeSetting;
import com.teddyhack.ui.clickgui.JColor;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class ColorMain extends Module {
    private static final Module ColorMain = null;
    public static ModeSetting colorModel = new ModeSetting("penis right?", ColorMain, "HSB", "RGB", "HSB");

    public ColorMain() {
        super ("colorMain", "world of colors", Keyboard.KEY_NONE, Category.CLIENT);
        this.addSetting(colorModel);
    }

    public void setup() {
        ArrayList<String> tab = new ArrayList<>();
        tab.add("Black");
        tab.add("Dark Green");
        tab.add("Dark Red");
        tab.add("Gold");
        tab.add("Dark Gray");
        tab.add("Green");
        tab.add("Red");
        tab.add("Yellow");
        tab.add("Dark Blue");
        tab.add("Dark Aqua");
        tab.add("Dark Purple");
        tab.add("Gray");
        tab.add("Blue");
        tab.add("Aqua");
        tab.add("Light Purple");
        tab.add("White");
        ArrayList<String> models=new ArrayList<>();
        models.add("RGB");
        models.add("HSB");
    }

    public void onEnable(){

    }

    private static TextFormatting settingToFormatting () {
        return TextFormatting.AQUA;
    }

    public static TextFormatting getFriendColor(){
        return settingToFormatting();
    }

    public static TextFormatting getEnemyColor() {
        return settingToFormatting();
    }

    public static TextFormatting getEnabledColor(){return settingToFormatting();}

    public static TextFormatting getDisabledColor(){return settingToFormatting();}

    private static Color settingToColor () {
        return Color.cyan;
    }

    public static JColor getFriendGSColor(){
        return new JColor(0xffffffff);
    }

    public static JColor getEnemyGSColor(){
        return new JColor(0xffffffff);
    }
}
