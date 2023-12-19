package com.upb.reservationmanagmentservice.events;

import com.upb.reservationmanagmentservice.entity.Reservation;
import com.upb.reservationmanagmentservice.handlers.EventHandler;
import com.upb.reservationmanagmentservice.handlers.ReservationEventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationEvent implements Event {
    private final Reservation reservation;
}
