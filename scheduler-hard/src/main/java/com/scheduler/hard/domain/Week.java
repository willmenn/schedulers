package com.scheduler.hard.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.scheduler.hard.domain.Days.FRI;
import static com.scheduler.hard.domain.Days.MON;
import static com.scheduler.hard.domain.Days.SAT;
import static com.scheduler.hard.domain.Days.SUN;
import static com.scheduler.hard.domain.Days.THU;
import static com.scheduler.hard.domain.Days.TUE;
import static com.scheduler.hard.domain.Days.WED;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Week {
    private final Day sun;
    private final Day mon;
    private final Day tue;
    private final Day wed;
    private final Day thu;
    private final Day fri;
    private final Day sat;

    public Week(int size) {
        this.sun = new Day(size);
        this.mon = new Day(size);
        this.tue = new Day(size);
        this.wed = new Day(size);
        this.thu = new Day(size);
        this.fri = new Day(size);
        this.sat = new Day(size);
    }

    public boolean addPersonIntoDay(Function<Week, Day> getDay, Shifts shift, Integer id) {
        return getDay.apply(this).addUniquePerson(id, shift.getShift());
    }

    public List<DayShiftPeopleTriple> getScheduleWithPersons(Set<Person> persons) {
        return Days.all.stream()
                .map(d -> applyForEachShift(persons, d))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private Set<DayShiftPeopleTriple> applyForEachShift(Set<Person> persons, Days d) {
        return Shifts.all.stream()
                .map(shift -> new DayShiftPeopleTriple(d, shift, getPersonsScheduled(persons, d, shift)))
                .collect(toSet());
    }

    private Set<Person> getPersonsScheduled(Set<Person> persons, Days d, Shifts shift) {
        return d.getFuncDay().apply(this).getPersonsScheduled(persons, shift.getShift());
    }

    Day getSun() {
        return sun;
    }

    Day getMon() {
        return mon;
    }

    Day getTue() {
        return tue;
    }

    Day getWed() {
        return wed;
    }

    Day getThu() {
        return thu;
    }

    Day getFri() {
        return fri;
    }

    Day getSat() {
        return sat;
    }
}
