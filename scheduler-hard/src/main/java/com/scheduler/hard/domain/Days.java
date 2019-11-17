package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.function.Function;

public enum Days {
    SUN(Week::getSun, 0),
    MON(Week::getMon, 1),
    TUE(Week::getTue, 2),
    WED(Week::getWed, 3),
    THU(Week::getThu, 4),
    FRI(Week::getFri, 5),
    SAT(Week::getSat, 6);

    public static final EnumSet<Days> all = EnumSet.allOf(Days.class);

    private final Function<Week, Day> day;
    private final int order;

    Days(Function<Week, Day> day, int order) {
        this.day = day;
        this.order = order;
    }

    public Function<Week, Day> getFuncDay() {
        return day;
    }

    int getOrder() {
        return order;
    }
}
