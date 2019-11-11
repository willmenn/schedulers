package com.scheduler.hard.domain;

import com.scheduler.hard.domain.time.Days;

import java.util.Objects;
import java.util.Set;

public class Person {
    private final Integer id;
    private final Set<Days> exclusionList;

    public Person(Integer id, Set<Days> exclusionList) {
        this.id = id;
        this.exclusionList = exclusionList;
    }

    public Integer getId() {
        return id;
    }

    public boolean isDayInExclusionList(Days day) {
        return this.exclusionList
                .stream()
                .anyMatch(d -> d.equals(day));
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
