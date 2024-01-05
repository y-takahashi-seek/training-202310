package jp.seekengine.trainingjava.controller.request;
//task5

import java.io.Serializable;

public record SampleTimeRequest(
        StartTime startTime,
        Duration duration,
        String requestTimeZoneId,
        String responseTimeZoneId
) implements Serializable {
    public record Duration(
            int hour, int minute, int second
    ) implements Serializable {
    }

    public record StartTime(
            int year, int month, int date, int hour, int minute, int second
    ) implements Serializable {
    }
}





