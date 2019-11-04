package com.scheduler.hard.domain;

import com.scheduler.hard.domain.time.Days;
import com.scheduler.hard.domain.time.Week;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
                .filter(person::isDayInExclusionList)
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
    }
}
