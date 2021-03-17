package com.teddyware.api.event.events;

import com.teddyware.api.event.Event;

public class EventNotifier extends Event<EventNotifier> {

    public String name;
    public boolean toggle;

    public EventNotifier(String name, boolean toggle) {
        this.name = name;
        this.toggle = toggle;
    }
}
