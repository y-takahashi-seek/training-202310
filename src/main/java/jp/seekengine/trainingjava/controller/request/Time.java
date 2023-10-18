package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Time(
        Number year,
        Number month,
        Number date,
        Number hour,
        Number minute,
        Number second
) implements Serializable {
}
