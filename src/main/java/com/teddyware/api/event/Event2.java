package com.teddyware.api.event;

import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;

public class Event2 extends Cancellable {

    private Era era = Era.PRE;
    private final float partialTicks;

    private Minecraft mc = Minecraft.getMinecraft();

    public Event2() {
        partialTicks = mc.getRenderPartialTicks();
    }

    public Event2(Era p_Era) {
        partialTicks = mc.getRenderPartialTicks();
        era = p_Era;
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
