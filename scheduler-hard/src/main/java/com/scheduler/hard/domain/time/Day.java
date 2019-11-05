package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_LIST;


public class Day {
    private final Days day;
    private final List<Integer> spots;
    private final Integer max;

    public Day(Days day, Integer max) {
        this.day = day;
        this.max = max;
        this.spots = new ArrayList<>(max);
    }

    public boolean addUniquePerson(Integer id) {
        Set<Integer> uniqueIntegers = this.convertSpotsToSet();
        if (uniqueIntegers.contains(id)) {
            return false;
        } else if (isDayFull()) {
            return false;
        } else {
            spots.add(id);
            return true;
        }
    }

    public boolean isPersonAtSchedule(Integer id) {
        return this.spots.stream()
                .anyMatch(i -> i.equals(id));

    }

    public boolean isDayEmpty() {
        return this.spots.size() == 0;
    }

    public boolean isDayFull() {
        return this.spots.size() == this.max;
    }

    public Days getDay() {
        return this.day;
    }

    public List<Person> getPersonsScheduled(Set<Person> persons) {
        if (isDayEmpty()) {
            return EMPTY_LIST;
        }

        Set<Integer> uniqueIds = convertSpotsToSet();

        return persons
                .stream()
                .filter(person -> uniqueIds.contains(person.getId()))
                .collect(Collectors.toList());
    }

    private Set<Integer> convertSpotsToSet() {
        return new HashSet<>(this.spots);
    }
}
