package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record ScheduleItemResponse(
        Long id, String title, String fromDatetime, String toDatetime
) implements Serializable {
}