package com.scheduler.hard.domain;

import com.scheduler.hard.domain.Day.Shifts;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

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

    Week(int size) {
        this.sun = new Day(size);
        this.mon = new Day(size);
        this.tue = new Day(size);
        this.wed = new Day(size);
        this.thu = new Day(size);
        this.fri = new Day(size);
        this.sat = new Day(size);
    }

    boolean addPersonIntoDay(Function<Week, Day> getDay, Function<Day, Shift> getShift, Integer id) {
        return getDay.apply(this).addUniquePerson(id, getShift);
    }

    List<DayShiftPeopleTriple> getScheduleWithPersons(Set<Person> persons) {
        return Days.all.stream()
                .map(d -> applyForEachShift(persons, d))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    boolean isPersonScheduled(Function<Week, Day> getDay, Function<Day, Shift> getShift, Integer id) {
        return getDay.apply(this).isPersonScheduled(getShift, id);
    }

    private Day getSun() {
        return sun;
    }

    private Day getMon() {
        return mon;
    }

    private Day getTue() {
        return tue;
    }

    private Day getWed() {
        return wed;
    }

    private Day getThu() {
        return thu;
    }

    private Day getFri() {
        return fri;
    }

    private Day getSat() {
        return sat;
    }

    private Set<DayShiftPeopleTriple> applyForEachShift(Set<Person> persons, Days d) {
        return Shifts.all.stream()
                .map(shift -> new DayShiftPeopleTriple(d, shift, getPersonsScheduled(persons, d, shift)))
                .collect(toSet());
    }

    private Set<Person> getPersonsScheduled(Set<Person> persons, Days d, Shifts shift) {
        return d.getFuncDay().apply(this).getPersonsScheduled(persons, shift.getShift());
    }

    public enum Days {
        SUN(Week::getSun, 0),
        MON(Week::getMon, 1),
        TUE(Week::getTue, 2),
        WED(Week::getWed, 3),
        THU(Week::getThu, 4),
        FRI(Week::getFri, 5),
        SAT(Week::getSat, 6);

        public static final EnumSet<Days> all = EnumSet
                .allOf(Days.class);

        private final Function<Week, Day> day;
        private final int order;

        Days(Function<Week, Day> day, int order) {
            this.day = day;
            this.order = order;
        }

        public Function<Week, Day> getFuncDay() {
            return day;
        }

        int getOrder() {
            return order;
        }
    }
}
