package com.scheduler.hard.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Shift {
    private Set<Integer> people;
    private Integer capacity;

    Shift(Integer capacity) {
        this.capacity = capacity;
        this.people = new HashSet<>();
    }

    boolean addUniquePeople(Integer id) {
        if (isCapacityFull()) {
            return false;
        }
        return this.people.add(id);
    }

    Set<Person> getPeoples(Set<Person> originalPeoples) {
        return originalPeoples
                .stream()
                .filter(p -> this.people.contains(p.getId()))
                .collect(Collectors.toSet());
    }

    boolean isPersonScheduled(Integer id) {
        return this.people.contains(id);
    }

    private boolean isCapacityFull() {
        return this.people.size() == capacity;
    }
}
