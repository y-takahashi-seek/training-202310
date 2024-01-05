package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;

//task3
public record TimesRequest(
        TimeDetail[] times,
        String requestTimeZoneId,
        String responseTimeZoneId
) implements Serializable {
}
