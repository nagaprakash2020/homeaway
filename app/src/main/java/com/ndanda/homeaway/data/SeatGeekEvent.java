package com.ndanda.homeaway.data;

import java.util.List;

public class SeatGeekEvent {
    private meta meta;
    private List<events> events;

    public com.ndanda.homeaway.data.meta getMeta() {
        return meta;
    }

    public void setMeta(com.ndanda.homeaway.data.meta meta) {
        this.meta = meta;
    }

    public List<com.ndanda.homeaway.data.events> getEvents() {
        return events;
    }

    public void setEvents(List<com.ndanda.homeaway.data.events> events) {
        this.events = events;
    }
}
