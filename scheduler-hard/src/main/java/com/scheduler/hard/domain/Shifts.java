package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.function.Function;

public enum Shifts {
    MORNING(Day::getMorning),
    AFTERNOON(Day::getAfternoon),
    NIGHT(Day::getNight);

    public static final EnumSet<Shifts> all = EnumSet.allOf(Shifts.class);

    private final Function<Day, Shift> shift;

    Shifts(Function<Day, Shift> shift) {
        this.shift = shift;
    }

    Function<Day, Shift> getShift() {
        return shift;
    }
}
