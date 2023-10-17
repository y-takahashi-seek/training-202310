package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;

public record yearMonthDateRequest (
    Number year,
    Number month,
    Number date,
    Number hour,
    Number minute,
    Number second
) implements Serializable {
}
