package jp.seekengine.trainingjava.controller.request;
//task4
import java.time.LocalDateTime;

public record EachTimeRequest(
       EachTimeDetail startTime,
        EachTimeDetail endTime
) {}
