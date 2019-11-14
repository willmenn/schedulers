package com.scheduler.hard.domain;

import java.util.Objects;
import java.util.Set;

public class Person {
    private final Integer id;
    private final Set<DayShiftTuple> dayShiftExclusionList;

    public Person(Integer id, Set<DayShiftTuple> dayShiftExclusionList) {
        this.id = id;
        this.dayShiftExclusionList = dayShiftExclusionList;
    }

    public Integer getId() {
        return id;
    }

    public boolean isDayAndShiftInExclusionList(Days day, Shifts shift) {
        return this.dayShiftExclusionList
                .stream()
                .anyMatch(s -> s.isEquals(day, shift));
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
}
