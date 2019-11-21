package com.scheduler.hard.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DayTest {

    @Test
    void shouldBeAbleToAddAUniquePersonToADay() {
        Day day = new Day(10);
        Assertions.assertTrue(day.addUniquePerson(1, Shifts.MORNING.getShift()));
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Day day = new Day(1);
        Assertions.assertTrue(day.addUniquePerson(1, Shifts.MORNING.getShift()));
        Assertions.assertFalse(day.addUniquePerson(2, Shifts.MORNING.getShift()));
    }

    @Test
    void shouldNotBeAbleToAddAPersonGivenSheIsNotUnique() {
        Day day = new Day(1);
        Assertions.assertTrue(day.addUniquePerson(1, Shifts.MORNING.getShift()));
        Assertions.assertFalse(day.addUniquePerson(1, Shifts.MORNING.getShift()));
    }


    @Test
    void shouldBeAbleToGetPersonsScheduledInThisDay() {
        Day day = new Day(2);
        day.addUniquePerson(1, Shifts.MORNING.getShift());
        day.addUniquePerson(2, Shifts.MORNING.getShift());

        Person p1 = new Person(1, new HashSet<>(), null);
        Person p2 = new Person(2, new HashSet<>(), null);
        Person p3 = new Person(3, new HashSet<>(), null);

        Set<Person> persons = new HashSet<>();
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);

        Set<Person> personsScheduled = day.getPersonsScheduled(persons, Shifts.MORNING.getShift());
        assertThat(personsScheduled)
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldNotBeAbleToGetPersonsScheduledGivenDayIsEmpty() {
        Day day = new Day(2);

        Person p1 = new Person(1, new HashSet<>(), null);
        Person p2 = new Person(2, new HashSet<>(), null);
        Person p3 = new Person(3, new HashSet<>(), null);

        Set<Person> persons = new HashSet<>();
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);

        Set<Person> personsScheduled = day.getPersonsScheduled(persons, Shifts.MORNING.getShift());
        assertThat(personsScheduled).isEmpty();
    }
}
