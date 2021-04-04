package com.teddyware.api.event.events;

import com.teddyware.api.event.Event;
import net.minecraft.util.EnumHandSide;

public class EventTransformSide extends Event {

    private final EnumHandSide enumHandSide;

    public EventTransformSide(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }

}
