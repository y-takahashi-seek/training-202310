package jp.seekengine.trainingjava.domain;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jp.seekengine.trainingjava.infrastructure.SampleRepository;
import jp.seekengine.trainingjava.infrastructure.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;

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

    public LocalDateTime localDateTimeFormat(int year, int month, int date, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, date, hour, minute, second);
    }

    public String zoneId(LocalDateTime localDateTime, String requestTimeZoneId, String responseTimeZoneId) {
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

    public LocalDateTime localDatetimeFormat(int year, int month, int date, int hour, int minute, int second) {
        return null;
    }
}







