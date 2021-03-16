package com.teddyhack.api.event.events;

import com.teddyhack.api.event.Event;
import net.minecraft.entity.MoverType;

public class EventMove extends Event<EventMove> {

    private MoverType moveType;

    public double x, y, z;

    public EventMove(MoverType type, double x, double y, double z) {
        this.moveType = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setMoveType(MoverType type) {
        this.moveType = type;
    }

    public void setX(double xPos) { this.x = xPos; }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double zPos) {
        this.z = zPos;
    }

    public MoverType getMoveType() {
        return this.moveType;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
