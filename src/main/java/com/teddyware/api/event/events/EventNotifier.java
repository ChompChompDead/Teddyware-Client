package com.teddyware.api.event.events;

import com.teddyware.api.event.Event2;
import com.teddyware.client.module.Module;

public class EventNotifier extends Event2 {

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
