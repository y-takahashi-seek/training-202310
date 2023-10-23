package jp.seekengine.trainingjava.controller.request;
//task3
public record TimeDetail(int year, int month, int date, int hour, int minute, int second) {

    public String toISO8601() {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d+09:00", year, month, date, hour, minute, second);
    }
}
