package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.function.Function;

public enum Shifts {
    MORNING(Day::getMorning, 0),
    AFTERNOON(Day::getAfternoon, 1),
    NIGHT(Day::getNight, 3);

    public static final EnumSet<Shifts> all = EnumSet.allOf(Shifts.class);

    private final Function<Day, Shift> shift;
    private final int order;

    Shifts(Function<Day, Shift> shift, int order) {
        this.shift = shift;
        this.order = order;
    }

    public Function<Day, Shift> getShift() {
        return shift;
    }

    int getOrder() {
        return order;
    }
}
