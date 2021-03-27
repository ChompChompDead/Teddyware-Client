package com.teddyware.api.event.events;

import com.teddyware.api.event.Event2;
import net.minecraft.network.Packet;

public class EventPacket extends Event2 {

    private final Packet<?> packet;

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public static class Send extends EventPacket {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Receive extends EventPacket{
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }

    public <T extends Packet<?>> T getPacket() {
        return (T)this.packet;
    }

}
