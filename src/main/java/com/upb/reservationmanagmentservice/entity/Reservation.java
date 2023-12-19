package com.upb.reservationmanagmentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="RESERVATIONS")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="TABLE_ID")
    private Long tableId;
    @Column(name="CIENT_ID")
    private Long clientId;
    @Column(name="QUANTITY")
    private Long quantity;
    @Column(name="RESERVATION_NAME")
    private String reservationName;
    @Column(name="STATUS")
    private String status;
    @Column(name="RESERVATION_TIME")
    private Date reservationTime;
}
