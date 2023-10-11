package jp.seekengine.trainingjava.domain;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    public String createSampleMessage(String message1, String message2) {
        return "Messageとして「%s」と「%s」を受け取りました。".formatted(message1, message2);
    }

}
