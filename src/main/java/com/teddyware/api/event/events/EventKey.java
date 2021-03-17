package com.teddyware.api.event.events;

import com.teddyware.api.event.Event;

public class EventKey extends Event<EventKey> {

    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public EventKey(int code) {
        this.code = code;
    }

}
