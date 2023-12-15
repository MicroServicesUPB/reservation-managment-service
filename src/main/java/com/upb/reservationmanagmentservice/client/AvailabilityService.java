package com.upb.reservationmanagmentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AVAILABILITY-SERVICE/tables")
public interface AvailabilityService {
    @GetMapping("/{id}")
    public long getTableById(@PathVariable long tableId);
    @GetMapping("/quantity/{id}")
    public long getTableQuantityById(@PathVariable long tableId);
}
