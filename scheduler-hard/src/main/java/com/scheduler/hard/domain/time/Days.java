package com.scheduler.hard.domain.time;

import java.util.EnumSet;

public enum Days {
    SUN("sunday"),
    MON("monday"),
    TUE("tuesday"),
    WED("wednesday"),
    THU("thursday"),
    FRI("friday"),
    SAT("saturday");
    private final String name;

    public static final EnumSet<Days> all = EnumSet.allOf(Days.class);

    Days(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
