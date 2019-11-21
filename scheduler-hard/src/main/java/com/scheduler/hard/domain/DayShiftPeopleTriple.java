package com.scheduler.hard.domain;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class DayShiftPeopleTriple {
    private final Week.Days days;
    private final Shifts shifts;
    private final Set<Person> people;

    public DayShiftPeopleTriple(Week.Days days, Shifts shifts, Set<Person> people) {
        this.days = days;
        this.shifts = shifts;
        this.people = people;
    }

    public static Comparator<DayShiftPeopleTriple> comparator() {
        return Comparator.comparing(DayShiftPeopleTriple::getDaysOrder)
                .thenComparing(DayShiftPeopleTriple::getShiftsOrder);
    }

    private int getDaysOrder() {
        return days.getOrder();
    }

    private int getShiftsOrder() {
        return shifts.getOrder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayShiftPeopleTriple that = (DayShiftPeopleTriple) o;
        return days == that.days &&
                shifts == that.shifts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, shifts);
    }

    @Override
    public String toString() {
        return "{ Day=" + days + "| Shift=" + this.shifts + " | people=" + people.toString() + "}\n";
    }
}
