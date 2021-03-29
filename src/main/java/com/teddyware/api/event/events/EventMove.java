package com.teddyware.api.event.events;

import com.teddyware.api.event.Event;
import net.minecraft.entity.MoverType;

public class EventMove extends Event {

    private MoverType type;

    public double x, y, z;

    public EventMove(MoverType type, double x, double y, double z) {
        super();
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoverType getType() {
        return this.type;
    }

    public void setType(MoverType type) {
        this.type = type;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
