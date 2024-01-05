package jp.seekengine.trainingjava.controller.request;
//task5

import java.io.Serializable;

public record Duration(
        int hour, int minute, int second
) implements Serializable {
}
