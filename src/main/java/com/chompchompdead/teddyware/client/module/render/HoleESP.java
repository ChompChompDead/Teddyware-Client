package com.chompchompdead.teddyware.client.module.render;

import com.chompchompdead.teddyware.api.event.events.EventRender;
import com.chompchompdead.teddyware.api.util.GeometryMasks;
import com.chompchompdead.teddyware.api.util.TWTessellator;
import com.chompchompdead.teddyware.api.util.color.JColor;
import com.chompchompdead.teddyware.client.module.Category;
import com.chompchompdead.teddyware.client.module.Module;
import com.chompchompdead.teddyware.client.setting.settings.ColorSetting;
import com.chompchompdead.teddyware.client.setting.settings.ModeSetting;
import com.chompchompdead.teddyware.client.setting.settings.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Module.Data(name = "HoleESP", description = "See holes for crystal pvp.", key = Keyboard.KEY_NONE, category = Category.Render)
public class HoleESP extends Module {

    public NumberSetting height = new NumberSetting("Height", this, 0.1, 0.0, 1.0, 0.1);
    public ModeSetting style = new ModeSetting("Style", this, "Outline", "Fill", "Outline");
    public ColorSetting obsidianColor = new ColorSetting("ObsidianColor", this, new JColor(104, 255, 66));
    public ColorSetting bedrockColor = new ColorSetting("BedrockColor", this, new JColor(255, 66, 66));

    public HoleESP() {
        this.addSetting(height, style, obsidianColor, bedrockColor);
    }

    public BlockPos[] surroundOffset = {
            //new BlockPos(0, -1, 0), // down
            new BlockPos(0, 0, -1), // north
            new BlockPos(1, 0, 0), // east
            new BlockPos(0, 0, 1), // south
            new BlockPos(-1, 0, 0) // west
    };

    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

    @Override
    public void onUpdate() {
        if (safeHoles == null) {
            safeHoles = new ConcurrentHashMap<>();
        } else {
            safeHoles.clear();
        }

        int range = (int) Math.ceil(8);

        List<BlockPos> blockPosList = getSphere(getPlayerPos(), range, range, false, true, 0);
        for (BlockPos pos : blockPosList){

            if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)){
                continue;
            }

            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            boolean isSafe = true;
            boolean isBedrock = true;

            for (BlockPos offset : surroundOffset) {
                Block block = mc.world.getBlockState(pos.add(offset)).getBlock();

                if (block != Blocks.BEDROCK){
                    isBedrock = false;
                }

                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isSafe = false;
                    break;
                }

                if (block == Blocks.AIR) {
                    isSafe = false;
                    break;
                }
            }

            if (isSafe){
                safeHoles.put(pos, isBedrock);
            }
        }
    }

    @Override
    public void onWorldRender(final EventRender event) {
        if (mc.player == null || safeHoles == null){
            return;
        }

        if (safeHoles.isEmpty()) {
            return;
        }

        safeHoles.forEach((blockPos, isBedrock) -> {
            drawBox(blockPos, 1, isBedrock);
        });

        safeHoles.forEach((blockPos, isBedrock) -> {
            drawOutline(blockPos,2, isBedrock);
        });
    }

    private JColor getColor (boolean isBedrock) {
        JColor c;
        if (isBedrock)
            c = bedrockColor.getValue();
        else
            c = obsidianColor.getValue();

        return new JColor(c);

    }

    private void drawBox(BlockPos blockPos, int width, boolean isBedrock) {
        JColor color = getColor(isBedrock);
        if(style.is("Fill")) {
            TWTessellator.drawBox(blockPos, height.getValue(), color, GeometryMasks.Quad.ALL);
        }
    }

    private void drawOutline(BlockPos blockPos, int width, boolean isBedrock) {
        JColor color = getColor(isBedrock);
        if(style.is("Outline")) {
            TWTessellator.drawBoundingBox(blockPos, height.getValue(), width, color);
        }
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++){
            for (int z = cz - (int) r; z <= cz + r; z++){
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++){
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))){
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    @Override
    public String getArrayListInfo() {
        return style.getMode();
    }

}
