package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.function.Function;

public enum Days {
    SUN("sunday", Week::getSun),
    MON("monday", Week::getMon),
    TUE("tuesday", Week::getTue),
    WED("wednesday", Week::getWed),
    THU("thursday", Week::getThu),
    FRI("friday", Week::getFri),
    SAT("saturday", Week::getSat);

    public static final EnumSet<Days> all = EnumSet.allOf(Days.class);

    private final String name;
    private final Function<Week, Day> day;


    Days(String name, Function<Week, Day> day) {
        this.name = name;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public Function<Week, Day> getDay() {
        return day;
    }
}
