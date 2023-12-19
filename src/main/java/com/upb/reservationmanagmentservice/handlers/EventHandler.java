package com.upb.reservationmanagmentservice.handlers;

import com.upb.reservationmanagmentservice.events.Event;
import com.upb.reservationmanagmentservice.events.ReservationCreatedEvent;

public interface EventHandler {

    void handle(Event event);
}