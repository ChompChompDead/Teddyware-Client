package com.chompchompdead.teddyware.api.event.events;

import com.chompchompdead.teddyware.api.event.Event;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventLiquidCollisionBB extends Event {

    private AxisAlignedBB boundingBox;
    private BlockPos blockPos;

    public EventLiquidCollisionBB() {
        super();
    }

    public EventLiquidCollisionBB(BlockPos blockPos) {
        super();
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
