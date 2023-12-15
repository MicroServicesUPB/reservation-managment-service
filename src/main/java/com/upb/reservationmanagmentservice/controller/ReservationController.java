package com.upb.reservationmanagmentservice.controller;

import com.upb.reservationmanagmentservice.model.ReservationRequest;
import com.upb.reservationmanagmentservice.model.ReservationResponse;
import com.upb.reservationmanagmentservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Long> addReservation(@RequestBody ReservationRequest reservationRequest) {
        long reservationId = reservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(reservationId, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity <ReservationResponse> getReservationById(@PathVariable("id") long reservationId){
        ReservationResponse reservationResponse = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }
    @GetMapping("/date/{scheduleId}")
    public ResponseEntity <List<ReservationResponse>> getReservationsByDate(@PathVariable("date") long scheduleId){
        List<ReservationResponse> reservations = reservationService.getReservationsByDate(scheduleId);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @GetMapping("/table/{tableId}")
    public ResponseEntity <List<ReservationResponse>> getReservationsByTable(@PathVariable("table") long tableId){
        List<ReservationResponse> reservations = reservationService.getReservationsByTable(tableId);
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

}

