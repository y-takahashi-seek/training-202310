package jp.seekengine.trainingjava.domain;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {
    public String createSampleMessage(String message1, String message2) {
        return "Messageとして「%s」と「%s」を受け取りました。".formatted(message1, message2);
    }
//    public  String  TimeZoneId1(String message,String message4) {
//         ZoneId reqZoneId = ZoneId.of(.requestTimeZoneId()),
//        ZoneId resZoneId = ZoneId.of(.responseTimeZoneId());
//         return resZoneId,reqZoneId;
//    }


    public LocalDateTime localdatetimeformatt(int a, int b, int c, int d, int e, int f) {
        LocalDateTime localDateTime1 = LocalDateTime.of(a, b, c, d, e, f);

        return localDateTime1;
    }

    public String zoneid(LocalDateTime localDateTime, String requestTimeZoneId, String responseTimeZoneId) {
        ZoneId requestZoneId = ZoneId.of(requestTimeZoneId);
        ZoneId responseZoneId = ZoneId.of(responseTimeZoneId);

        ZonedDateTime requestZonedDateTime = localDateTime.atZone(requestZoneId);
        ZonedDateTime responseZonedDateTime = requestZonedDateTime.withZoneSameInstant(responseZoneId);

        return responseZonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    }



}




