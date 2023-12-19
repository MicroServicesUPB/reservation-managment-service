package com.upb.reservationmanagmentservice.events;

import com.upb.reservationmanagmentservice.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventEmitter {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void emitEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}