package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;

//task2
public record YearMonthDateRequest(
        int year,
        int month,
        int date,
        int hour,
        int minute,
        int second,
        String requestTimeZoneId,
        String responseTimeZoneId
) implements Serializable {

}
