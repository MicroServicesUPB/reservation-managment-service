package com.upb.reservationmanagmentservice.handlers;

import com.upb.reservationmanagmentservice.events.ReservationEvent;

public interface EventHandler {
    void handle(ReservationEvent event);
}