package com.teddyware.api.event;

import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;

public class Event extends Cancellable {

    private Era era = Era.PRE;
    private final float partialTicks;

    private Minecraft mc = Minecraft.getMinecraft();

    public Event() {
        partialTicks = mc.getRenderPartialTicks();
    }

    public Event(Era era) {
        partialTicks = mc.getRenderPartialTicks();
        era = era;
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public enum Era {
        PRE,
        PERI,
        POST
    }

}
