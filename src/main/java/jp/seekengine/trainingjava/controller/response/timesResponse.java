package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record timesResponse(
        String[] convertedTimes
) implements Serializable {}
