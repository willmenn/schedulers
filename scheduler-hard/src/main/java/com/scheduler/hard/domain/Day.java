package com.scheduler.hard.domain;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.scheduler.hard.domain.Shifts.AFTERNOON;
import static com.scheduler.hard.domain.Shifts.MORNING;
import static com.scheduler.hard.domain.Shifts.NIGHT;

public class Day {
    private final Days day;
    private final Shift morning;
    private final Shift afternoon;
    private final Shift night;

    public Day(Days day, Integer capacity) {
        this.day = day;
        this.morning = new Shift(capacity, MORNING);
        this.afternoon = new Shift(capacity, AFTERNOON);
        this.night = new Shift(capacity, NIGHT);
    }
    public Days getDay() {
        return this.day;
    }

    boolean addUniquePerson(Integer id, Function<Day, Shift> getShift) {
        return getShift.apply(this).addUniquePeople(id);
    }

    Set<Person> getPersonsScheduled(Set<Person> peoples) {
        return Shifts.all.stream()
                .map(shifts -> shifts.getShift().apply(this).getPeoples(peoples))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    Shift getMorning() {
        return morning;
    }

    Shift getAfternoon() {
        return afternoon;
    }

    Shift getNight() {
        return night;
    }
}
