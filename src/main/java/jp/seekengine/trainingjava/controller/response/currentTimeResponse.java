package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record currentTimeResponse(
        String currentTime
) implements Serializable {}


