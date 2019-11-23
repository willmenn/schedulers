package com.scheduler.hard.domain;

import com.scheduler.hard.domain.Day.Shifts;
import com.scheduler.hard.domain.Week.Days;

import java.util.HashSet;
import java.util.Set;

public class PlaceDayShiftTuple {
    private final Days day;
    private final Set<Shifts> shifts;

    public PlaceDayShiftTuple(Days day, Shifts morning, Shifts afternoon, Shifts night) {
        this.day = day;
        this.shifts = new HashSet<>();
        addShiftIfNotNull(this.shifts, morning);
        addShiftIfNotNull(this.shifts, afternoon);
        addShiftIfNotNull(this.shifts, night);
    }

    boolean isDayShiftEquals(Days day, Shifts shift) {
        return this.day.equals(day) && this.shifts.stream().anyMatch(s -> s.equals(shift));
    }

    private void addShiftIfNotNull(Set<Shifts> shifts, Shifts shift) {
        if (shifts != null && shift != null) {
            shifts.add(shift);
        }
    }
}
