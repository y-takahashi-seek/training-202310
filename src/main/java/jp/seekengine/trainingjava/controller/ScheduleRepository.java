package jp.seekengine.trainingjava.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.fromDatetime > :fromDatetime AND s.toDatetime < :toDatetime")
    List<Schedule> findSchedulesByTimeRange(@Param("fromDatetime") String fromDatetime, @Param("toDatetime") String toDatetime);
}

