package com.upb.reservationmanagmentservice.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequest {
    private Long tableId;
    private String status;
    private Date reservationTime;
    private Long quantity;
    private String reservationName;
    private Long clientId;
}
