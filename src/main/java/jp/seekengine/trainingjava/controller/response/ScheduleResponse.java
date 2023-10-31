package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record ScheduleResponse(
        Long id, String title, String fromDatetime, String toDatetime
)implements Serializable {}