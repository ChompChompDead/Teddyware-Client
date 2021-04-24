package com.chompchompdead.teddyware.client.module.render;

import com.chompchompdead.teddyware.api.event.events.EventRender;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@Module.Data(name = "Xray", description = "See ores inside the ground.", key = Keyboard.KEY_NONE, category = Category.Render)
public class Xray extends Module {

    public static ArrayList<Block> xrayBlocks;

    public Xray() {
        initXray();
    }

    @Override
    public void onEnable() {
        if (mc.world == null) return;
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        if (mc.world == null) return;
        mc.renderGlobal.loadRenderers();
    }

    public void initXray() {
        xrayBlocks = new ArrayList<>();

        xrayBlocks.add(Blocks.DIAMOND_ORE);
        xrayBlocks.add(Blocks.COAL_ORE);
        xrayBlocks.add(Blocks.GOLD_ORE);
        xrayBlocks.add(Blocks.IRON_ORE);
        xrayBlocks.add(Blocks.LAPIS_ORE);
        xrayBlocks.add(Blocks.REDSTONE_ORE);
        xrayBlocks.add(Blocks.LIT_REDSTONE_ORE);
        xrayBlocks.add(Blocks.QUARTZ_ORE);
        xrayBlocks.add(Blocks.WATER);
        xrayBlocks.add(Blocks.FLOWING_WATER);
        xrayBlocks.add(Blocks.LAVA);
        xrayBlocks.add(Blocks.FLOWING_LAVA);
    }
}
