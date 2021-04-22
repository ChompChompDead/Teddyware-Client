package com.chompchompdead.teddyware.api.event.events;

import com.chompchompdead.teddyware.api.event.Event;

public class EventTravel extends Event {

    public float strafe, vertical, forward;

    public EventTravel(float strafe, float vertical, float forward) {
        super();
        this.strafe = strafe;
        this.vertical = vertical;
        this.forward = forward;
    }

}
