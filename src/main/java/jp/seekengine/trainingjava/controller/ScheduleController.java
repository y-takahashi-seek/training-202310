package jp.seekengine.trainingjava.controller;

import jp.seekengine.trainingjava.controller.request.SampleRequest;
import jp.seekengine.trainingjava.controller.response.SampleResponse;
import jp.seekengine.trainingjava.controller.response.currentTimeResponse;
import jp.seekengine.trainingjava.domain.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    public currentTimeResponse sample1(){
        //現在時刻作成処理
        LocalDateTime now = LocalDateTime.now();
        // レスポンス用のオブジェクトを作成
        String datetime= now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00"));
      return new currentTimeResponse(datetime);
    }
}
