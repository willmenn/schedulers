package com.scheduler.hard.domain;

import java.util.Objects;
import java.util.Set;

public class DayShiftPeopleTriple {
    private final Days days;
    private final Shifts shifts;
    private final Set<Person> people;

    public DayShiftPeopleTriple(Days days, Shifts shifts, Set<Person> people) {
        this.days = days;
        this.shifts = shifts;
        this.people = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayShiftPeopleTriple that = (DayShiftPeopleTriple) o;
        return days == that.days;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }

    @Override
    public String toString() {
        return "{ Day=" + days + "| Shift=" + this.shifts + " | people=" + people + "}\n";
    }
}
