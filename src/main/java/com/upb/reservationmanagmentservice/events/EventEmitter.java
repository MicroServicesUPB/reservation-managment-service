package com.upb.reservationmanagmentservice.events;

import com.upb.reservationmanagmentservice.handlers.EventHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventEmitter {
    private final List<EventHandler> handlers = new ArrayList<>();

    public void addHandler(EventHandler handler) {
        handlers.add(handler);
    }

    public void emit(Event event) {
        for (EventHandler handler : handlers) {
            handler.handle((ReservationEvent) event);
        }
    }
}