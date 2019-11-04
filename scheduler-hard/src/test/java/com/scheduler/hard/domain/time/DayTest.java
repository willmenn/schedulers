package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.time.Days.MON;
import static org.assertj.core.api.Assertions.*;

class DayTest {

    @Test
    void shouldBeAbleToCheckIfDayIsEmpty() {
        Day day = new Day(MON, 10);
        Assertions.assertTrue(day.isDayEmpty());
    }

    @Test
    void shouldBeAbleToCheckIfDayIsNotEmpty() {
        Day day = new Day(MON, 10);
        day.addUniquePerson(1);
        Assertions.assertFalse(day.isDayEmpty());
    }

    @Test
    void shouldBeAbleToCheckIfDayIsFull() {
        Day day = new Day(MON, 1);
        day.addUniquePerson(1);
        Assertions.assertTrue(day.isDayFull());
    }

    @Test
    void shouldBeAbleToCheckIfDayIsNotFull() {
        Day day = new Day(MON, 10);
        Assertions.assertFalse(day.isDayFull());
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsAtTheDay() {
        Day day = new Day(MON, 10);
        day.addUniquePerson(1);
        Assertions.assertTrue(day.isPersonAtSchedule(1));
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsNotAtTheDay() {
        Day day = new Day(MON, 10);
        day.addUniquePerson(1);
        Assertions.assertFalse(day.isPersonAtSchedule(2));
    }

    @Test
    void shouldBeAbleToAddAUniquePersonToADay() {
        Day day = new Day(MON, 10);
        Assertions.assertTrue(day.addUniquePerson(1));
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Day day = new Day(MON, 1);
        Assertions.assertTrue(day.addUniquePerson(1));
        Assertions.assertFalse(day.addUniquePerson(2));
    }

    @Test
    void shouldNotBeAbleToAddAPersonGivenSheIsNotUnique() {
        Day day = new Day(MON, 1);
        Assertions.assertTrue(day.addUniquePerson(1));
        Assertions.assertFalse(day.addUniquePerson(1));
    }


    @Test
    void shouldBeAbleToGetPersonsScheduledInThisDay() {
        Day day = new Day(MON, 2);
        day.addUniquePerson(1);
        day.addUniquePerson(2);

        Person p1 = new Person(1);
        Person p2 = new Person(2);
        Person p3 = new Person(3);

        Set<Person> persons = new HashSet<>();
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);

        List<Person> personsScheduled = day.getPersonsScheduled(persons);
        assertThat(personsScheduled)
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldNotBeAbleToGetPersonsScheduledGivenDayIsEmpty() {
        Day day = new Day(MON, 2);

        Person p1 = new Person(1);
        Person p2 = new Person(2);
        Person p3 = new Person(3);

        Set<Person> persons = new HashSet<>();
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);

        List<Person> personsScheduled = day.getPersonsScheduled(persons);
        assertThat(personsScheduled).isEmpty();
    }

    @Test
    void shouldBeAbleToGetDays() {
        Day day = new Day(MON, 2);
        Assertions.assertEquals(MON, day.getDay());
    }
}
