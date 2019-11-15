package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.function.Function;

public enum Days {
    SUN(Week::getSun),
    MON(Week::getMon),
    TUE(Week::getTue),
    WED(Week::getWed),
    THU(Week::getThu),
    FRI(Week::getFri),
    SAT(Week::getSat);

    public static final EnumSet<Days> all = EnumSet.allOf(Days.class);

    private final Function<Week, Day> day;

    Days(Function<Week, Day> day) {
        this.day = day;
    }

    public Function<Week, Day> getFuncDay() {
        return day;
    }
}
