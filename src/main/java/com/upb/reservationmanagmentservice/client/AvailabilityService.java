package com.upb.reservationmanagmentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "AVAILABILITY-SERVICE/tables")
public interface AvailabilityService {
    @GetMapping("/{id}")
    public TableDTO getTableById(@PathVariable long tableId);
    @GetMapping("/quantity/{id}")
    public long getTableQuantityById(@PathVariable long tableId);
    @GetMapping()
    public List<TableDTO> getAllTables();
}
