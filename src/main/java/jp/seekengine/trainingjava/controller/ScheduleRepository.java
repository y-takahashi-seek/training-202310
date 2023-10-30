package jp.seekengine.trainingjava.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.seekengine.trainingjava.controller.Schedule;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

