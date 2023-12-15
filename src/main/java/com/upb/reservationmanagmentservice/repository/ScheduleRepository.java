package com.upb.reservationmanagmentservice.repository;

import com.upb.reservationmanagmentservice.entity.Reservation;
import com.upb.reservationmanagmentservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
