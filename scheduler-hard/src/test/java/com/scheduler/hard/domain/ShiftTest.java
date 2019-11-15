package com.scheduler.hard.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShiftTest {

    private Shift shift;

    @BeforeEach
    void setUp() {
        shift = new Shift(2, Shifts.AFTERNOON);
    }

    @Test
    void shouldBeAbleToAddUniquePersonIntoShift() {
        boolean isPersonAdded = shift.addUniquePeople(1);
        assertTrue(isPersonAdded);
    }

    @Test
    void shouldNotBeAbleToAddUniquePersonGivenThatPersonIsAlreadyThere() {
        shift.addUniquePeople(1);
        boolean isPersonAdded = shift.addUniquePeople(1);
        assertFalse(isPersonAdded);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenShiftIsFull() {
        shift.addUniquePeople(1);
        shift.addUniquePeople(2);
        boolean isPersonAdded = shift.addUniquePeople(3);
        assertFalse(isPersonAdded);
    }

    @Test
    void shouldBeAbleToGetThePeopleInShift() {
        shift.addUniquePeople(1);
        shift.addUniquePeople(2);

        Set<Person> peoples = new HashSet<>();
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        Person person3 = new Person(3, new HashSet<>());
        peoples.add(person1);
        peoples.add(person2);
        peoples.add(person3);

        Set<Person> response = shift.getPeoples(peoples);
        Assertions.assertThat(response)
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }
}
