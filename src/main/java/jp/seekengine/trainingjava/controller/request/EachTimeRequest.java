package jp.seekengine.trainingjava.controller.request;

import java.time.LocalDateTime;

public record EachTimeRequest(
       EachTimeDetail startTime,
        EachTimeDetail endTime
) {}
