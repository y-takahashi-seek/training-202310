package jp.seekengine.trainingjava.controller;

import jp.seekengine.trainingjava.controller.request.*;
import jp.seekengine.trainingjava.controller.response.*;
import jp.seekengine.trainingjava.domain.ScheduleService;
import jp.seekengine.trainingjava.infrastructure.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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
        
        LocalDateTime now = LocalDateTime.now();
      
        String datetime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00"));
        return new currentTimeResponse(datetime);
    }

    @GetMapping("/times/current/convert")
    public convertedTimeResponse sample2(@RequestBody yearMonthDateRequest Samplerequest) {



        try {
           
            LocalDateTime localDateTime = scheduleService.localdatetimeformatt(Samplerequest.year(), Samplerequest.month(), Samplerequest.date(),
                    Samplerequest.hour(), Samplerequest.minute(), Samplerequest.second());


            ZonedDateTime zonedDateTime = ZonedDateTime.parse(scheduleService.zoneid(localDateTime, Samplerequest.requestTimeZoneId(), Samplerequest.responseTimeZoneId()));
          
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            String convertedTime = formatter.format(zonedDateTime);
            return new convertedTimeResponse(convertedTime);
        } catch (DateTimeException e) {
        
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
            
            SampleTimeRequest.StartTime startTime = sampleTimeRequest.startTime();
            SampleTimeRequest.Duration duration = sampleTimeRequest.duration();
         
            LocalDateTime startLocalDateTime = scheduleService.localdatetimeformat(startTime.year(), startTime.month(), startTime.date(),
                    startTime.hour(), startTime.minute(), startTime.second());

            ZonedDateTime zonedDateTime = ZonedDateTime.parse(scheduleService.zoneId(startLocalDateTime,
                    sampleTimeRequest.requestTimeZoneId(), sampleTimeRequest.responseTimeZoneId()));
            ZonedDateTime endZonedDateTime = zonedDateTime.plusHours(duration.hour())
                    .plusMinutes(duration.minute())
                    .plusSeconds(duration.second())
                    .withZoneSameInstant(ZoneId.from(zonedDateTime.withZoneSameInstant(ZoneId.of(sampleTimeRequest.responseTimeZoneId()))));
            String formattedEndTime = endZonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return new SampleTimeResponse(formattedEndTime);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }
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
