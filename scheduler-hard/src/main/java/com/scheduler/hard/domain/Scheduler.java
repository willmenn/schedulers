package com.scheduler.hard.domain;

import com.scheduler.hard.domain.time.Days;
import com.scheduler.hard.domain.time.Week;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Scheduler {

    public Week schedule(Set<Person> persons, Week week) {
        persons.stream()
                .map(this::getPersonByDay)
                .flatMap(Collection::stream)
                .forEach(tuple -> week.addPersonIntoDay(tuple.getDay(), tuple.getId()));

        return week;
    }

    private Set<PersonTuple> getPersonByDay(Person person) {
        return Days.all
                .stream()
                .filter(d -> !person.isDayInExclusionList(d))
                .map(d -> new PersonTuple(d, person.getId()))
                .collect(Collectors.toSet());
    }

    private class PersonTuple {
        private final Days day;
        private final Integer id;

        PersonTuple(Days day, Integer id) {
            this.day = day;
            this.id = id;
        }

        private Days getDay() {
            return day;
        }

        private Integer getId() {
            return id;
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
