package com.scheduler.hard.domain.time;

public enum Days {
    SUN("sunday"),
    MON("monday"),
    TUE("tuesday"),
    WED("wednesday"),
    THU("thursday"),
    FRI("friday"),
    SAT("saturday");
    private final String name;

    Days(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
