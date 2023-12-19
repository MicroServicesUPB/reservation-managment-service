package com.upb.reservationmanagmentservice.service;

import com.upb.reservationmanagmentservice.client.TableDTO;
import com.upb.reservationmanagmentservice.model.ReservationRequest;
import com.upb.reservationmanagmentservice.model.ReservationResponse;

import java.util.List;

public interface ReservationService {
    long createReservation(ReservationRequest reservationRequest);
    ReservationResponse getReservationById(long reservationId);

    List<TableDTO> getAvailableTablesByDate(String date);

    List<ReservationResponse> getReservationsByDate(long scheduleId);
    List<ReservationResponse> getReservationsByTable( long tableId);

    boolean approveReservation(long id);

    boolean rejectReservation(long id);
}
