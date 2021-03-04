package com.teddyhack.event.listeners;

import com.teddyhack.event.Event;

public class EventNotifier extends Event<EventNotifier> {

    public String name;
    public boolean toggle;

    public EventNotifier(String name, boolean toggle) {
        this.name = name;
        this.toggle = toggle;
    }
}
