package com.scheduler.hard.domain;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

public class Day {
    private final Shift morning;
    private final Shift afternoon;
    private final Shift night;

    Day(Integer capacity) {
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

    public enum Shifts {
        MORNING(Day::getMorning, 0),
        AFTERNOON(Day::getAfternoon, 1),
        NIGHT(Day::getNight, 3);

        public static final EnumSet<Shifts> all = EnumSet.allOf(Shifts.class);

        private final Function<Day, Shift> shift;
        private final int order;

        Shifts(Function<Day, Shift> shift, int order) {
            this.shift = shift;
            this.order = order;
        }

        public Function<Day, Shift> getShift() {
            return shift;
        }

        int getOrder() {
            return order;
        }
    }
}
