package jp.seekengine.trainingjava.controller;

import jp.seekengine.trainingjava.controller.request.SampleRequest;
import jp.seekengine.trainingjava.controller.request.TimesRequest;
import jp.seekengine.trainingjava.controller.request.TimeDetail;

import jp.seekengine.trainingjava.controller.request.yearMonthDateRequest;
import jp.seekengine.trainingjava.controller.response.SampleResponse;
import jp.seekengine.trainingjava.controller.response.convertedTimeResponse;
import jp.seekengine.trainingjava.controller.response.currentTimeResponse;
import jp.seekengine.trainingjava.controller.response.timesResponse;
import jp.seekengine.trainingjava.domain.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//    @GetMapping("/times/current/convert")
//    public convertedTimeResponse sample2(@RequestBody yearMonthDateRequest Samplerequest) {
//
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
//    }

    @GetMapping("/times/current/convert")
    public timesResponse sample3(@RequestBody TimesRequest request) {
        String[] convertedTimes = Arrays.stream(request.times())
                .map(TimeDetail::toISO8601)
                .toArray(String[]::new);
        return new timesResponse(convertedTimes);
    }

}

