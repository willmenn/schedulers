package com.scheduler.hard.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Shift {
    private Set<Integer> people;
    private Integer capacity;
    private Shifts shift;

    public Shift(Integer capacity, Shifts shift) {
        this.capacity = capacity;
        this.shift = shift;
        this.people = new HashSet<>();
    }

    public boolean addUniquePeople(Integer id) {
        if (isCapacityFull()) {
            return false;
        }
        return this.people.add(id);
    }

    public Set<Person> getPeoples(Set<Person> originalPeoples) {
        return originalPeoples
                .stream()
                .filter(p -> this.people.contains(p.getId()))
                .collect(Collectors.toSet());
    }

    private boolean isCapacityFull() {
        return this.people.size() == capacity;
    }
}
