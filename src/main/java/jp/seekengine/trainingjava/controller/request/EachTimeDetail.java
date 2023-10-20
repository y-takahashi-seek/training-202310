package jp.seekengine.trainingjava.controller.request;

public record EachTimeDetail(
         Number year,
         Number month,
         Number date,
         Number hour,
         Number minute,
         Number second
        // getters and setters
){    public int yearAsInt() {
    return year.intValue();
}

    public int monthAsInt() {
        return month.intValue();
    }

    public int dateAsInt() {
        return date.intValue();
    }

    public int hourAsInt() {
        return hour.intValue();
    }

    public int minuteAsInt() {
        return minute.intValue();
    }

    public int secondAsInt() {
        return second.intValue();
    }
}

