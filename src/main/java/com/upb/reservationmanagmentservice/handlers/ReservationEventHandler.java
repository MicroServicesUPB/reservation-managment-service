package com.upb.reservationmanagmentservice.handlers;

import com.upb.reservationmanagmentservice.client.NotificationService;
import com.upb.reservationmanagmentservice.events.ReservationEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;


@Component
public class ReservationEventHandler implements EventHandler {
    private NotificationService notificationService;
    @Override
    public void handle(ReservationEvent event) {
        // Handle the reservation-created event
        System.out.println("Reservation created: " + event.getReservation());

        if (event instanceof ReservationEvent) {
            handleReservationCreatedEvent(event);
        }
    }
    private void handleReservationCreatedEvent(ReservationEvent reservationCreatedEvent) {
        // Handle the reservation-created event
        System.out.println("Reservation created with ID: " + reservationCreatedEvent.getReservation().getId());
        Instant i = Instant.now();
        notificationService.createNotification(reservationCreatedEvent.getReservation().getId(), reservationCreatedEvent.getReservation().getClientId(), "Pendiente", "Reserva Creada, espera a que sea aprobada por un admin, gracias!", Date.from(i));
        // You can perform additional actions here, such as sending notifications, updating statistics, etc.
    }
}