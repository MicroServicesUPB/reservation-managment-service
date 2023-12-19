package com.upb.reservationmanagmentservice.controller;

import com.upb.reservationmanagmentservice.model.ReservationRequest;
import com.upb.reservationmanagmentservice.model.ReservationResponse;
import com.upb.reservationmanagmentservice.service.ReservationService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Long> addReservation(@RequestBody ReservationRequest reservationRequest) {
        logger.info("Reservation with ID aaaaaaaaaaaaaaaaaaaaaaaaa");

        long reservationId = reservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(reservationId, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity <ReservationResponse> getReservationById(@PathVariable long id){
        ReservationResponse reservationResponse = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }
    @GetMapping("/date/{scheduleId}")
    public ResponseEntity <List<ReservationResponse>> getReservationsByDate(@PathVariable long scheduleId){
        List<ReservationResponse> reservations = reservationService.getReservationsByDate(scheduleId);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @GetMapping("/table/{tableId}")
    public ResponseEntity <List<ReservationResponse>> getReservationsByTable(@PathVariable long tableId){
        List<ReservationResponse> reservations = reservationService.getReservationsByTable(tableId);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveReservation(@PathVariable long id) {
        // Assuming you have a method in your service to handle the approval
        boolean isApproved = reservationService.approveReservation(id);

        if (isApproved) {
            return new ResponseEntity<>("Reservation approved successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to approve reservation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectReservation(@PathVariable long id) {
        boolean isRejected = reservationService.rejectReservation(id);

        if (isRejected) {
            return new ResponseEntity<>("Reservation rejected successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to reject reservation (not found or invalid state)", HttpStatus.NOT_FOUND);
        }
    }
    /*
        @GetMapping("/available/{scheduleId}")
    public ResponseEntity <List<TableEntity>> getAvailableTables(@PathVariable long scheduleId){
        List<TableEntity> tables = availabilityService.getAvailableTables(scheduleId);
        if (tables.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }
    @GetMapping("/{id}/{scheduleId}")
    public ResponseEntity <TableEntity> getTableAvailabilityByIdAndDate(@PathVariable long id, @PathVariable long scheduleId){
        TableEntity tableEntity = availabilityService.getTableAvailabilityByIdAndDate(id, scheduleId);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }
         */

}

