package jp.seekengine.trainingjava.controller.request;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//task3
public record TimeDetail(int year, int month, int date, int hour, int minute, int second) {
    public String convertTimeZone(String requestTimeZoneId, String responseTimeZoneId) {
        try {
            LocalDateTime localDateTime = LocalDateTime.of(year, month, date, hour, minute, second);
            ZoneId requestZoneId = ZoneId.of(requestTimeZoneId);
            ZoneId responseZoneId = ZoneId.of(responseTimeZoneId);

            ZonedDateTime requestZonedDateTime = localDateTime.atZone(requestZoneId);
            ZonedDateTime responseZonedDateTime = requestZonedDateTime.withZoneSameInstant(responseZoneId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            return formatter.format(responseZonedDateTime);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date/time or timezone");
        }
    }

    public String toISO8601() {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d+09:00", year, month, date, hour, minute, second);
    }
}
