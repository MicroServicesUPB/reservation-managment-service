package com.upb.reservationmanagmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private Long ReservationId;
    private Long tableId;
    private String status;
    private Date reservationTime;
    private Long quantity;
    private String reservationName;
}
