package jp.seekengine.trainingjava.controller;

import jp.seekengine.trainingjava.controller.request.MessageRequest;
import jp.seekengine.trainingjava.controller.request.SampleRequest;
import jp.seekengine.trainingjava.controller.response.SampleResponse;
import jp.seekengine.trainingjava.domain.ScheduleService;
import jp.seekengine.trainingjava.infrastructure.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jp.seekengine.trainingjava.controller.request.*;

import jp.seekengine.trainingjava.controller.response.*;
import jp.seekengine.trainingjava.domain.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        //レスポンスを時間に変換
        int year = Integer.parseInt(Samplerequest.year().toString());
        int month = Integer.parseInt(Samplerequest.month().toString());
        int date = Integer.parseInt(Samplerequest.date().toString());
        int hour = Integer.parseInt(Samplerequest.hour().toString());
        int minute = Integer.parseInt(Samplerequest.minute().toString());
        int second = Integer.parseInt(Samplerequest.second().toString());
        // 日時オブジェクトを生成
        LocalDateTime localDateTime = LocalDateTime.of(year, month, date, hour, minute, second);

        // 日本標準時に変換
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        LocalDateTime convertedDateTime = localDateTime.atZone(zoneId).toLocalDateTime();

        // ISO 8601 拡張形式に変換
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'+09:00'");
        String convertedTimes = formatter.format(convertedDateTime);

        return new convertedTimeResponse(convertedTimes);
    }

    @GetMapping("/times/convert")
    public timesResponse sample3(@RequestBody TimesRequest request) {
        String[] convertedTimes = Arrays.stream(request.times())
                .map(TimeDetail::toISO8601)
                .toArray(String[]::new);
        return new timesResponse(convertedTimes);
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
        //StartTimeとDurationをインナーレコードにした形式
        SampleTimeRequest.StartTime startTime = sampleTimeRequest.startTime();
        SampleTimeRequest.Duration duration = sampleTimeRequest.duration();
        //jp.seekengine.trainingjava.controller.request.Duration duration = sampleTimeRequest.duration();
        ZonedDateTime startDateTime = ZonedDateTime.of(
                startTime.year(),
                startTime.month(),
                startTime.date(),
                startTime.hour(),
                startTime.minute(),
                startTime.second(),
                0,
                ZoneId.of("Asia/Tokyo")
        );

        ZonedDateTime endDateTime = startDateTime.plusHours(duration.hour())
                .plusMinutes(duration.minute())
                .plusSeconds(duration.second());


        return new SampleTimeResponse(endDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PostMapping("/message")
    public MessageEntity sample(@RequestBody MessageRequest message) {
        return scheduleService.createMessage(message.message());
    }

    @GetMapping("/messages/{id}")
    public MessageEntity samples(@PathVariable Integer id) {
        return scheduleService.getMessageById(id);
    }

    @GetMapping("/messages/search")
    public List<MessageEntity> sampleSearch(@RequestParam String message) {
        return scheduleService.searchMessage(message);
    }

}
