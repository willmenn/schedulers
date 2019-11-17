package com.scheduler.hard;

import com.scheduler.hard.domain.Day;
import com.scheduler.hard.domain.Days;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Shifts;
import com.scheduler.hard.domain.Week;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Scheduler {

    public Week schedule(Set<Person> persons, Week week) {
        persons.stream()
                .map(this::createTuplePersonByDayShift)
                .flatMap(Collection::stream)
                .forEach(tuple -> week.addPersonIntoDay(tuple.getDayFunc(), tuple.getShift(), tuple.getId()));

        return week;
    }

    private Set<PersonTuple> createTuplePersonByDayShift(Person person) {
        return Days.all
                .stream()
                .map(d -> addShifts(person, d))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<PersonTuple> addShifts(Person person, Days d) {
        return Shifts.all.stream()
                .filter(shift -> !person.isDayAndShiftInExclusionList(d, shift))
                .map(shift -> new PersonTuple(d.getFuncDay(), person.getId(), shift))
                .collect(Collectors.toSet());
    }

    private class PersonTuple {
        private final Function<Week, Day> day;
        private final Integer id;
        private final Shifts shift;

        PersonTuple(Function<Week, Day> day, Integer id, Shifts shift) {
            this.day = day;
            this.id = id;
            this.shift = shift;
        }

        private Function<Week, Day> getDayFunc() {
            return day;
        }

        private Integer getId() {
            return id;
        }

        private Shifts getShift() {
            return shift;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonTuple that = (PersonTuple) o;
            return day == that.day &&
                    Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(day, id);
        }
    }
}
