package com.teddyware.api.event.events;

import com.teddyware.api.event.Event;
import com.teddyware.client.module.Module;

public class EventNotifier extends Event {

    public Module module;

    public EventNotifier(final Module mod) {
        super();
        module = mod;
    }

    public static class EventNotifierEnable extends EventNotifier {
        public EventNotifierEnable(Module p_Mod) {
            super(p_Mod);
        }
    }

    public static class EventNotifierDisable extends EventNotifier {
        public EventNotifierDisable(Module p_Mod) {
            super(p_Mod);
        }
    }
}
