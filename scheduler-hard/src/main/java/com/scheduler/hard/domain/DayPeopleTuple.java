package com.scheduler.hard.domain;

import java.util.Objects;
import java.util.Set;

public class DayPeopleTuple {
    private final Days days;
    private final Set<Person> people;

    public DayPeopleTuple(Days days, Set<Person> people) {
        this.days = days;
        this.people = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayPeopleTuple that = (DayPeopleTuple) o;
        return days == that.days;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }

    @Override
    public String toString() {
        return "{ Day=" + days + " people=" + people + "}\n";
    }
}
