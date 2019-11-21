package com.scheduler.hard.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class Place {
    private final Integer id;
    private final Week week;

    public Place(Integer id, int size) {
        this.id = id;
        this.week = new Week(size);
    }

    public boolean addUniquePerson(Function<Week, Day> getDay, Function<Day, Shift> getShift, Integer id) {
        return this.week.addPersonIntoDay(getDay, getShift, id);
    }

    public boolean isPersonScheduled(Function<Week, Day> getDay, Function<Day, Shift> getShift, Integer id) {
        return this.week.isPersonScheduled(getDay, getShift, id);
    }

    public List<DayShiftPeopleTriple> getPersonsScheduled(Set<Person> persons) {
        return this.week.getScheduleWithPersons(persons);
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(id, place.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
