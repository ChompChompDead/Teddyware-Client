package com.teddyhack.event.listeners;

import com.teddyhack.event.Event;

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
