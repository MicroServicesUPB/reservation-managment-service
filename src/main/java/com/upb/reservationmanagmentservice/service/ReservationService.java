package com.upb.reservationmanagmentservice.service;

import com.upb.reservationmanagmentservice.model.ReservationRequest;
import com.upb.reservationmanagmentservice.model.ReservationResponse;

import java.util.List;

public interface ReservationService {
    long createReservation(ReservationRequest reservationRequest);
    ReservationResponse getReservationById(long reservationId);
    List<ReservationResponse> getReservationsByDate(long scheduleId);
    List<ReservationResponse> getReservationsByTable( long tableId);

}
