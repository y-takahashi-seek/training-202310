package jp.seekengine.trainingjava.domain;

import jp.seekengine.trainingjava.controller.request.SampleTimeRequest;
import jp.seekengine.trainingjava.controller.response.SampleTimeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {
    public String createSampleMessage(String message1, String message2) {
        return "Messageとして「%s」と「%s」を受け取りました。".formatted(message1, message2);
    }

}
