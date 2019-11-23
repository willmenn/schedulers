package com.scheduler.hard.domain;

import com.scheduler.hard.domain.Day.Shifts;
import com.scheduler.hard.domain.Week.Days;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Person {
    private final Integer id;
    private final Set<PlaceDayShiftTuple> dayShiftExclusionList;
    private final Set<Integer> placeIds;

    public Person(Integer id, Set<PlaceDayShiftTuple> dayShiftExclusionList, Set<Integer> placeIds) {
        this.id = id;
        this.dayShiftExclusionList = Objects.requireNonNullElseGet(dayShiftExclusionList, HashSet::new);
        this.placeIds = Objects.requireNonNullElseGet(placeIds, HashSet::new);
    }

    public Integer getId() {
        return id;
    }

    public boolean isDayAndShiftInExclusionList(Days day, Shifts shift) {
        return this.dayShiftExclusionList
                .stream()
                .anyMatch(s -> s.isDayShiftEquals(day, shift));
    }

    public boolean isPlaceInExclusionList(Integer id) {
        return this.placeIds.contains(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                '}';
    }
}
