package com.teddyhack.api.event.events;

import com.teddyhack.api.event.Event;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventLiquidCollisionBB extends Event<EventLiquidCollisionBB> {

    private AxisAlignedBB boundingBox;
    private BlockPos blockPos;

    public EventLiquidCollisionBB() {

    }

    public EventLiquidCollisionBB(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

}
