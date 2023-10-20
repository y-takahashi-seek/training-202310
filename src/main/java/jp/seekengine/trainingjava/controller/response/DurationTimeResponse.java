package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record DurationTimeResponse(
        String duration
) implements Serializable {
}
