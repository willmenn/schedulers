package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Optional<Integer> isPersonAtSchedule(Integer id) {
        return this.spots.stream()
                .filter(i -> i.equals(id))
                .findFirst();

    }

    public boolean isDayEmpty() {
        return  this.spots.size() == 0;
    }

    public boolean isDayFull() {
        return  this.spots.size() == this.max;
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
