package com.upb.reservationmanagmentservice.events;

import com.upb.reservationmanagmentservice.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationCreatedEvent implements Event {
    private final Reservation reservation;
}
