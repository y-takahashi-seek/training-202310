package jp.seekengine.trainingjava.domain;

import jp.seekengine.trainingjava.controller.request.SampleTimeRequest;
import jp.seekengine.trainingjava.controller.response.SampleTimeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jp.seekengine.trainingjava.infrastructure.SampleRepository;
import jp.seekengine.trainingjava.infrastructure.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {

    private final SampleRepository sampleRepository;

    @Autowired
    public ScheduleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

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

    public MessageEntity createMessage(String message) {
        MessageEntity entity = new MessageEntity();
        entity.setMessage(message);
        return sampleRepository.save(entity);
    }

    public MessageEntity getMessageById(Integer id) {
        return sampleRepository.findById(id).get();
    }

    public List<MessageEntity> searchMessage(String message) {
        return sampleRepository.findByMessageContaining(message);
    }


}




