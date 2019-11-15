package com.scheduler.hard.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.Days.MON;
import static com.scheduler.hard.domain.Days.TUE;
import static com.scheduler.hard.domain.Shifts.MORNING;
import static org.assertj.core.api.Assertions.assertThat;

class WeekTest {

    @Test
    void shouldBeAbleToAddPersonIntoDayGivenDayIsNotFull() {
        Week week = new Week(2);
        Person person = new Person(1, new HashSet<>());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON.getFuncDay(), MORNING, person.getId());
        Assertions.assertTrue(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Week week = new Week(1);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(2, new HashSet<>());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person1.getId());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON.getFuncDay(), MORNING, person2.getId());
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenPersonWasAlreadyInThatDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        Person person2 = new Person(1, new HashSet<>());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person1.getId());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON.getFuncDay(), MORNING, person2.getId());
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldBeAbleToGetDaysByPerson() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person1.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);

        List<DayPeopleTuple> scheduled = week.getScheduleWithPersons(persons);
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
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person1.getId());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person2.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        List<DayPeopleTuple> scheduled = week.getScheduleWithPersons(persons);
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
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person1.getId());
        week.addPersonIntoDay(TUE.getFuncDay(), MORNING, person2.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        List<DayPeopleTuple> scheduled = week.getScheduleWithPersons(persons);
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
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person1.getId());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person2.getId());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING, person3.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        List<DayPeopleTuple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("persons")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }
}
