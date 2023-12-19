package com.upb.reservationmanagmentservice.service;

import com.upb.reservationmanagmentservice.client.AvailabilityService;
import com.upb.reservationmanagmentservice.client.TableDTO;
import com.upb.reservationmanagmentservice.entity.Reservation;
import com.upb.reservationmanagmentservice.events.EventEmitter;
import com.upb.reservationmanagmentservice.events.ReservationCreatedEvent;
import com.upb.reservationmanagmentservice.events.ReservationUpdatedEvent;
import com.upb.reservationmanagmentservice.model.ReservationRequest;
import com.upb.reservationmanagmentservice.model.ReservationResponse;
import com.upb.reservationmanagmentservice.repository.ReservationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private EventEmitter eventEmitter;
    @Autowired
    private AvailabilityService availabilityService;
    @Override

    public long createReservation(ReservationRequest reservationRequest) {

        List<ReservationResponse> reservationsList = reservationRepository.findAll().stream()
                .filter((reservationEntity -> reservationEntity.getTableId().equals(reservationRequest.getTableId())&&reservationEntity.getReservationTime().equals(reservationRequest.getReservationTime())&&reservationEntity.getStatus().equals("Approved")))
                .map(reservationEntity -> {
                    ReservationResponse reservation = new ReservationResponse();
                    return reservation;
                }).collect(Collectors.toList());

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
        ReservationCreatedEvent reservationCreatedEvent = new ReservationCreatedEvent(reservation);
        eventEmitter.emitEvent(reservationCreatedEvent);
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
    public List<TableDTO> getAvailableTablesByDate(String date) {
        // Get all reservations for the given date
        List<Reservation> reservations = reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getReservationTime().equals(date))
                .collect(Collectors.toList());

        // Get all tables
        List<TableDTO> allTables = availabilityService.getAllTables();

        // Filter out tables that have reservations for the given date
        List<TableDTO> availableTables = allTables.stream()
                .filter(table -> reservations.stream().noneMatch(reservation -> reservation.getTableId().equals(table.getId())))
                .collect(Collectors.toList());

        return availableTables;
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

    @Override
    public boolean approveReservation(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();

            if (reservation.getStatus().equals("Pending")) {
                reservation.setStatus("Approved");
                ReservationUpdatedEvent reservationUpdatedEvent = new ReservationUpdatedEvent(reservation,reservation.getStatus());
                eventEmitter.emitEvent(reservationUpdatedEvent);
                reservationRepository.save(reservation);

                return true;
            }
        }
        return false;
    }
    @Override
    public boolean rejectReservation(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            if (reservation.getStatus().equals("Pending")) {
                reservation.setStatus("Rejected");
                ReservationUpdatedEvent reservationUpdatedEvent = new ReservationUpdatedEvent(reservation,reservation.getStatus());
                eventEmitter.emitEvent(reservationUpdatedEvent);
                reservationRepository.save(reservation);
                return true;
            }
        }
        return false;
    }
}
