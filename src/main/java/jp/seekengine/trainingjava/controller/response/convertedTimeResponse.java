package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record convertedTimeResponse(

        String  convertedTimes
)implements Serializable {}
