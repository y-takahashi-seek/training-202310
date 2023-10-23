package jp.seekengine.trainingjava.controller;

import jp.seekengine.trainingjava.controller.request.*;

import jp.seekengine.trainingjava.controller.response.*;
import jp.seekengine.trainingjava.domain.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.lang.reflect.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.Duration;
import java.time.LocalDateTime;
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/sample")
    public SampleResponse sample(@RequestBody SampleRequest sampleRequest) {
        String message = scheduleService.createSampleMessage(sampleRequest.sampleField1(), sampleRequest.sampleField2());
        return new SampleResponse(message);
    }

    @GetMapping("/times/current")
    public currentTimeResponse sample1() {
        //現在時刻作成処理
        LocalDateTime now = LocalDateTime.now();
        // レスポンス用のオブジェクトを作成
        String datetime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00"));
        return new currentTimeResponse(datetime);
    }

    @GetMapping("/times/current/convert")
    public convertedTimeResponse sample2(@RequestBody yearMonthDateRequest Samplerequest) {

//        //レスポンスを時間に変換
//        int year = Integer.parseInt(Samplerequest.year().toString());
//        int month = Integer.parseInt(Samplerequest.month().toString());
//        int date = Integer.parseInt(Samplerequest.date().toString());
//        int hour = Integer.parseInt(Samplerequest.hour().toString());
//        int minute = Integer.parseInt(Samplerequest.minute().toString());
//        int second = Integer.parseInt(Samplerequest.second().toString());
//        // 日時オブジェクトを生成
//        LocalDateTime localDateTime = LocalDateTime.of(year, month, date, hour, minute, second);
//
//        // 日本標準時に変換
//        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
//        LocalDateTime convertedDateTime = localDateTime.atZone(zoneId).toLocalDateTime();
//
//        // ISO 8601 拡張形式に変換
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'+09:00'");
//        String convertedTimes = formatter.format(convertedDateTime);
//
//        return new convertedTimeResponse(convertedTimes);
        try {
            // リクエストから日時とタイムゾーンを取得
            LocalDateTime localDateTime = LocalDateTime.of(
                    Samplerequest.year(), Samplerequest.month(), Samplerequest.date(),
                    Samplerequest.hour(), Samplerequest.minute(), Samplerequest.second()
            );
            ZoneId requestZoneId = ZoneId.of(Samplerequest.requestTimeZoneId());
            ZoneId responseZoneId = ZoneId.of(Samplerequest.responseTimeZoneId());

            // リクエストのタイムゾーンを考慮して日時を変換
            ZonedDateTime zonedDateTime = localDateTime.atZone(requestZoneId);
            ZonedDateTime convertedDateTime = zonedDateTime.withZoneSameInstant(responseZoneId);

            // ISO 8601 拡張形式に変換してレスポンスを返却
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            String convertedTime = formatter.format(convertedDateTime);
            return new convertedTimeResponse(convertedTime);
        } catch (DateTimeException e) {
            // 不正なタイムゾーンIDが指定された場合など
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date/time or timezone");
        }
    }


    @GetMapping("/times/convert")
    public timesResponse sample3(@RequestBody TimesRequest request) {
        try {
            String[] convertedTimes = Arrays.stream(request.times())
                    .map(timeDetail -> timeDetail.convertTimeZone(request.requestTimeZoneId(), request.responseTimeZoneId()))
                    .toArray(String[]::new);
            return new timesResponse(convertedTimes);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



    @GetMapping("/duration")
    public DurationTimeResponse sample4(@RequestBody EachTimeRequest eachtimeRequest) {
        LocalDateTime startDateTime = toLocalDateTime(eachtimeRequest.startTime());
        LocalDateTime endDateTime = toLocalDateTime(eachtimeRequest.endTime());

        Duration duration = Duration.between(startDateTime, endDateTime);

        String iso8601Duration = formatToISO8601(duration);

        return new DurationTimeResponse(iso8601Duration);
    }

    private LocalDateTime toLocalDateTime(EachTimeDetail detail) {
        return LocalDateTime.of(
                detail.yearAsInt(),
                detail.monthAsInt(),
                detail.dateAsInt(),
                detail.hourAsInt(),
                detail.minuteAsInt(),
                detail.secondAsInt()
        );
    }

    private String formatToISO8601(Duration duration) {
        long days = duration.toDays();
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long seconds = duration.getSeconds();

        StringBuilder result = new StringBuilder("P");

        if (days > 0) {
            result.append(days).append("D");
        }

        if (hours > 0 || minutes > 0 || seconds > 0) {
            result.append("T");
        }

        if (hours > 0) {
            result.append(hours).append("H");
        }

        if (minutes > 0) {
            result.append(minutes).append("M");
        }

        if (seconds > 0) {
            result.append(seconds).append("S");
        }

        return result.toString();
    }

    @GetMapping("/end")
    public SampleTimeResponse calculateEndTime(@RequestBody SampleTimeRequest sampleTimeRequest) {
        try {
            //StartTimeとDurationをインナーレコードにした形式
            SampleTimeRequest.StartTime startTime = sampleTimeRequest.startTime();
            SampleTimeRequest.Duration duration = sampleTimeRequest.duration();
            //jp.seekengine.trainingjava.controller.request.Duration duration = sampleTimeRequest.duration();

            ZoneId requestZoneId = ZoneId.of(sampleTimeRequest.requestTimeZoneId());
            ZoneId responseZoneId = ZoneId.of(sampleTimeRequest.responseTimeZoneId());

            LocalDateTime startLocalDateTime = LocalDateTime.of(
                    startTime.year(), startTime.month(), startTime.date(),
                    startTime.hour(), startTime.minute(), startTime.second()
            );

            ZonedDateTime startZonedDateTime = ZonedDateTime.of(startLocalDateTime, requestZoneId);
            ZonedDateTime endZonedDateTime = startZonedDateTime.plusHours(duration.hour())
                    .plusMinutes(duration.minute())
                    .plusSeconds(duration.second())
                    .withZoneSameInstant(responseZoneId);

            String formattedEndTime = endZonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return new SampleTimeResponse(formattedEndTime);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }
    }
}



