package com.upb.reservationmanagmentservice.client;

import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationService {
    @PostMapping()
    public void createNotification(@PathVariable long reservationId, @PathVariable long clientId, @PathVariable String message, @PathVariable Date timeStamp);

}
