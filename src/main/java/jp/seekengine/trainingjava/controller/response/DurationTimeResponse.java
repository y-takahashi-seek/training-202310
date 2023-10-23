package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;
//task4
public record DurationTimeResponse(
        String duration
) implements Serializable {
}
