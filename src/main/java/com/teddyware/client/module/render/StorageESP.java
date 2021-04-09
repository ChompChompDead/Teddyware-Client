package com.teddyware.client.module.render;

import com.teddyware.api.event.events.EventRender;
import com.teddyware.api.util.GeometryMasks;
import com.teddyware.api.util.TWTessellator;
import com.teddyware.api.util.color.JColor;
import com.teddyware.client.module.Category;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.settings.ColorSetting;
import com.teddyware.client.setting.settings.ModeSetting;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

@Module.Data(name = "StorageESP", description = "See storage objects (helpful for stash finding.)", key = Keyboard.KEY_NONE, category = Category.Render)
public class StorageESP extends Module {

    public ModeSetting style = new ModeSetting("Style", this, "Outline", "Fill", "Outline");
    public ColorSetting chestColor = new ColorSetting("Chests", this, new JColor(255, 141, 48, 50));
    public ColorSetting enderChestColor = new ColorSetting("EnderChests", this, new JColor(183, 48, 255, 50));
    public ColorSetting shulkerBoxColor = new ColorSetting("ShulkerBoxes", this, new JColor(255, 48, 48, 50));
    public ColorSetting otherColor = new ColorSetting("Other", this, new JColor(161, 161, 161, 50));
    public JColor containerBox;
    public JColor containerColor;
    public int opacityGradient;

    public StorageESP() {
        this.addSetting(style, chestColor, enderChestColor, shulkerBoxColor, otherColor);
        this.arrayListInfo = style.getMode();
    }

    public void onWorldRender(EventRender e) {
        if (style.is("Outline")) {
            mc.world.loadedTileEntityList.stream().filter(tileEntity -> rangeTileCheck(tileEntity)).forEach(tileEntity -> {
                if (tileEntity instanceof TileEntityChest) {
                    containerColor = new JColor(chestColor.getValue(), 255);
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                }
                if (tileEntity instanceof TileEntityEnderChest) {
                    containerColor = new JColor(enderChestColor.getValue(), 255);
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                }
                if (tileEntity instanceof TileEntityShulkerBox) {
                    containerColor = new JColor(shulkerBoxColor.getValue(), 255);
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                }
                if (tileEntity instanceof TileEntityDispenser || tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityHopper || tileEntity instanceof TileEntityDropper) {
                    containerColor = new JColor(otherColor.getValue(), 255);
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                }
            });
        }

        if (style.is("Fill")) {
            mc.world.loadedTileEntityList.stream().filter(tileEntity -> rangeTileCheck(tileEntity)).forEach(tileEntity -> {
                if (tileEntity instanceof TileEntityChest) {
                    containerColor = new JColor(chestColor.getValue(), 255);
                    containerBox = new JColor(chestColor.getValue());
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                    drawStorageBox(tileEntity.getPos(), 1, containerBox);
                }
                if (tileEntity instanceof TileEntityEnderChest) {
                    containerColor = new JColor(enderChestColor.getValue(), 255);
                    containerBox = new JColor(enderChestColor.getValue());
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                    drawStorageBox(tileEntity.getPos(), 1, containerBox);
                }
                if (tileEntity instanceof TileEntityShulkerBox) {
                    containerColor = new JColor(shulkerBoxColor.getValue(), 255);
                    containerBox = new JColor(shulkerBoxColor.getValue());
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                    drawBox(tileEntity.getPos(), 1, containerBox);
                }
                if (tileEntity instanceof TileEntityDispenser || tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityHopper || tileEntity instanceof TileEntityDropper) {
                    containerColor = new JColor(otherColor.getValue(), 255);
                    containerBox = new JColor(otherColor.getValue());
                    TWTessellator.drawBoundingBox(mc.world.getBlockState(tileEntity.getPos()).getSelectedBoundingBox(mc.world, tileEntity.getPos()), 2, containerColor);
                    drawBox(tileEntity.getPos(), 1, containerBox);
                }
            });
        }
    }

    public void drawStorageBox(BlockPos blockPos, int width, JColor color) {
        TWTessellator.drawStorageBox(blockPos, 0.88, color, GeometryMasks.Quad.ALL);
    }

    public void drawBox(BlockPos blockPos, int width, JColor color) {
        TWTessellator.drawBox(blockPos, 1, color, GeometryMasks.Quad.ALL);
    }

    public boolean rangeTileCheck(TileEntity tileEntity) {
        // your mom moment.
        if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > 100 * 100) {
            return false;
        }

        if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 32400) {
            opacityGradient = 50;
        } else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 16900 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 32400) {
            opacityGradient = 100;
        } else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 6400 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 16900) {
            opacityGradient = 150;
        } else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 900 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 6400) {
            opacityGradient = 200;
        } else {
            opacityGradient = 255;
        }

        return true;
    }
}
