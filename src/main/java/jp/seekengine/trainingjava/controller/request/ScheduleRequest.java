package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;

public record ScheduleRequest(
        String title, String fromDatetime, String toDatetime
)implements Serializable {}
