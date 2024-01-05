package jp.seekengine.trainingjava.controller.request;
//task5

import java.io.Serializable;

public record StartTime(
        int year, int month, int date, int hour, int minute, int second
) implements Serializable {
}