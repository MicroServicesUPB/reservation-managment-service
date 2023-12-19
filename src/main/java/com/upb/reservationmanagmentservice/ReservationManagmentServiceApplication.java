package com.upb.reservationmanagmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservationManagmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationManagmentServiceApplication.class, args);
	}

}
