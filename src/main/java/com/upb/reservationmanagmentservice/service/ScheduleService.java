package com.upb.reservationmanagmentservice.service;

import com.upb.reservationmanagmentservice.entity.Schedule;

import java.util.Date;
import java.util.Optional;


public interface ScheduleService {
    long addSchedule(Date date);
    Date getScheduleTime(long scheduleId);
}
