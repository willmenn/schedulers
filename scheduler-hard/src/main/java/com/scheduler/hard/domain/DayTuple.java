package com.scheduler.hard.domain;

import java.util.Set;

public class DayTuple {
    private final Days days;
    private final Set<Person> persons;

    DayTuple(Days days, Set<Person> persons) {
        this.days = days;
        this.persons = persons;
    }

    public Days getDays() {
        return days;
    }
}
