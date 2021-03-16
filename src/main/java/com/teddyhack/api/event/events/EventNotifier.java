package com.teddyhack.api.event.events;

import com.teddyhack.api.event.Event;

public class EventNotifier extends Event<EventNotifier> {

    public String name;
    public boolean toggle;

    public EventNotifier(String name, boolean toggle) {
        this.name = name;
        this.toggle = toggle;
    }
}
