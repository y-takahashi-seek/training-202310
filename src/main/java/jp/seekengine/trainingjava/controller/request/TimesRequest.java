package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//task3
public record TimesRequest(
        TimeDetail[] times,
        String requestTimeZoneId,
        String responseTimeZoneId
)implements Serializable {}
