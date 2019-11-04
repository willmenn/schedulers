package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.time.Days.MON;
import static com.scheduler.hard.domain.time.Days.TUE;
import static org.assertj.core.api.Assertions.assertThat;

class WeekTest {

    @Test
    void shouldBeAbleToAddPersonIntoDayGivenDayIsNotFull() {
        Week week = new Week(2);
        Person person = new Person(1, new HashSet<>());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON, person.getId());
        Assertions.assertTrue(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Week week = new Week(1);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON, person2.getId());
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenPersonWasAlreadyInThatDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(1, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON, person2.getId());
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsAtACertainDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());

        boolean isPersonScheduled = week.isPersonScheduledForDay(MON, person1);
        Assertions.assertTrue(isPersonScheduled);
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsNotAtACertainDayGivenDayIsEmpty() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());

        boolean isPersonScheduled = week.isPersonScheduledForDay(TUE, person1);
        Assertions.assertFalse(isPersonScheduled);
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsNotAtACertainDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());

        boolean isPersonScheduled = week.isPersonScheduledForDay(MON, person2);
        Assertions.assertFalse(isPersonScheduled);
    }

    @Test
    void shouldBeAbleToGetDaysByPerson() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);

        List<DayTuple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("persons")
                .extracting("id")
                .containsExactlyInAnyOrder(1);
    }

    @Test
    void shouldBeAbleToGetTwoPersonsInTheSameDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());
        week.addPersonIntoDay(MON, person2.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        List<DayTuple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("persons")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldBeAbleToGetTwoPersonsInDifferentDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());
        week.addPersonIntoDay(TUE, person2.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        List<DayTuple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("persons")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldBeAbleToGetTwoPersonsGivenExceedTheCapacity() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        Person person3 = new Person(3, new HashSet<>());
        week.addPersonIntoDay(MON, person1.getId());
        week.addPersonIntoDay(MON, person2.getId());
        week.addPersonIntoDay(MON, person3.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        List<DayTuple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("persons")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }
}
