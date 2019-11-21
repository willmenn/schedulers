package com.scheduler.hard.domain;

import java.util.Set;
import java.util.function.Function;

import static com.scheduler.hard.domain.Shifts.AFTERNOON;
import static com.scheduler.hard.domain.Shifts.MORNING;
import static com.scheduler.hard.domain.Shifts.NIGHT;

public class Day {
    private final Shift morning;
    private final Shift afternoon;
    private final Shift night;

    public Day(Integer capacity) {
        this.morning = new Shift(capacity);
        this.afternoon = new Shift(capacity);
        this.night = new Shift(capacity);
    }

    boolean addUniquePerson(Integer id, Function<Day, Shift> getShift) {
        return getShift.apply(this).addUniquePeople(id);
    }

    boolean isPersonScheduled(Function<Day, Shift> getShift, Integer id) {
        return getShift.apply(this).isPersonScheduled(id);
    }

    Set<Person> getPersonsScheduled(Set<Person> peoples, Function<Day, Shift> getShift) {
        return getShift.apply(this).getPeoples(peoples);
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
