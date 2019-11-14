package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.function.Function;

public enum Shifts {
    MORNING("morning", Day::getMorning),
    AFTERNOON("afternoon", Day::getAfternoon),
    NIGHT("night", Day::getNight);

    public static final EnumSet<Shifts> all = EnumSet.allOf(Shifts.class);

    private final String value;
    private final Function<Day, Shift> shift;

    Shifts(String value, Function<Day, Shift> shift) {
        this.value = value;
        this.shift = shift;
    }

    Function<Day, Shift> getShift() {
        return shift;
    }
}
