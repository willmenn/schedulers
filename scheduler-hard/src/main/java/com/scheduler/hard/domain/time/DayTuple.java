package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;

import java.util.List;

public class DayTuple {
    private final Days days;
    private final List<Person> persons;

    public DayTuple(Days days, List<Person> persons) {
        this.days = days;
        this.persons = persons;
    }

    public Days getDays() {
        return days;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
