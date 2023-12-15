package com.upb.reservationmanagmentservice.service;

import com.upb.reservationmanagmentservice.entity.Schedule;
import com.upb.reservationmanagmentservice.repository.ScheduleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
@Log4j2
public class ScheduleServiceImpl implements  ScheduleService{
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public long addSchedule(Date date) {
        Schedule schedule = Schedule.builder()
                .scheduleTime(date)
                .build();
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    @Override
    public Date getScheduleTime(long scheduleId) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        return schedule.get().getScheduleTime();
    }
}
