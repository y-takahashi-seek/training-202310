package jp.seekengine.trainingjava.controller.request;

public record EachTimeRequest(
        EachTimeDetail startTime,
        EachTimeDetail endTime
) {
}
