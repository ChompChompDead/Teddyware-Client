package com.chompchompdead.teddyware.api.event.events;

import com.chompchompdead.teddyware.api.event.Event;
import com.chompchompdead.teddyware.client.module.Module;

public class EventNotifier extends Event {

    public Module module;

    public EventNotifier(final Module mod) {
        super();
        module = mod;
    }

    public static class EventNotifierEnable extends EventNotifier {
        public EventNotifierEnable(Module mod) {
            super(mod);
        }
    }

    public static class EventNotifierDisable extends EventNotifier {
        public EventNotifierDisable(Module mod) {
            super(mod);
        }
    }
}
