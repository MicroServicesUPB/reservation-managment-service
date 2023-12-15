package com.upb.reservationmanagmentservice.service;

import com.upb.reservationmanagmentservice.client.AvailabilityService;
import com.upb.reservationmanagmentservice.client.ClientService;
import com.upb.reservationmanagmentservice.entity.Reservation;
import com.upb.reservationmanagmentservice.model.ReservationRequest;
import com.upb.reservationmanagmentservice.model.ReservationResponse;
import com.upb.reservationmanagmentservice.repository.ReservationRepository;
import com.upb.reservationmanagmentservice.repository.ScheduleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleService scheduleService;
    private AvailabilityService availabilityService;
    private ClientService clientService;
    @Override
    public long createReservation(ReservationRequest reservationRequest) {

        List<ReservationResponse> reservationsList = reservationRepository.findAll().stream()
                .filter((reservationEntity -> reservationEntity.getTableId().equals(reservationRequest.getTableId())&&reservationEntity.getReservationTime().equals(reservationRequest.getReservationTime())))
                .map(reservationEntity -> {
                    ReservationResponse reservation = new ReservationResponse();
                    return reservation;
                }).collect(Collectors.toList());

        clientService.getClientById(reservationRequest.getClientId());

        if(reservationRequest.getQuantity()>availabilityService.getTableQuantityById(reservationRequest.getTableId()) || reservationsList.isEmpty()) {
            throw new RuntimeException("Requested quantity exceeds available quantity for the table or isn't available at the requested time");
        }
            Reservation reservation = Reservation.builder()
                    .reservationName(reservationRequest.getReservationName())
                    .tableId(reservationRequest.getTableId())
                    .clientId(reservationRequest.getClientId())
                    .quantity(reservationRequest.getQuantity())
                    .build();
            reservationRepository.save(reservation);
            scheduleService.addSchedule(reservationRequest.getReservationTime());
            return reservation.getId();
    }

    @Override
    public ReservationResponse getReservationById(long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        ReservationResponse reservationResponse = new ReservationResponse();
        BeanUtils.copyProperties(reservation,reservationResponse);
        return reservationResponse;
    }

    @Override
    public List<ReservationResponse> getReservationsByDate(long scheduleId) {
        List<Reservation> reservationEntities = reservationRepository.findAll();
        List<ReservationResponse> reservationsList = reservationEntities .stream()
                .filter((reservationEntity -> reservationEntity.getReservationTime().equals(scheduleService.getScheduleTime(scheduleId))))
                .map(reservationEntity -> {
                    ReservationResponse reservation = new ReservationResponse();
                    BeanUtils.copyProperties(reservationEntity, reservation );
                    return reservation;
                }).collect(Collectors.toList());
        return reservationsList;
    }

    @Override
    public List<ReservationResponse> getReservationsByTable(long tableId) {
        List<Reservation> reservationEntities = reservationRepository.findAll();
        List<ReservationResponse> reservationsList = reservationEntities .stream()
                .filter((reservationEntity -> reservationEntity.getTableId().equals(tableId)))
                .map(reservationEntity -> {
                    ReservationResponse reservation = new ReservationResponse();
                    BeanUtils.copyProperties(reservationEntity, reservation );
                    return reservation;
                }).collect(Collectors.toList());
        return reservationsList;
    }
}
