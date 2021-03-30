package com.teddyware.client.module.combat;

import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class Surround extends Module {

    public Surround() {
        super("Surrond", "surronds yourself in OBSIDIAN", Keyboard.KEY_NONE, Category.Combat);
    }

    public BlockPos[] surroundBlocks = {
        new BlockPos(0, 0, -1), // north
        new BlockPos(1, 0, 0), // east
        new BlockPos(0, 0, 1), // south
        new BlockPos(-1, 0, 0) // west
    };

    @Override
    public void onUpdate() {
        for (BlockPos block : surroundBlocks) {

        }
    }
}
