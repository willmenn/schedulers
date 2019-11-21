package com.scheduler.hard.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.Shifts.MORNING;
import static com.scheduler.hard.domain.Week.Days.MON;
import static com.scheduler.hard.domain.Week.Days.TUE;
import static org.assertj.core.api.Assertions.assertThat;

class WeekTest {

    @Test
    void shouldBeAbleToAddPersonIntoDayGivenDayIsNotFull() {
        Week week = new Week(2);
        Person person = new Person(1, new HashSet<>(), null);
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person.getId());
        Assertions.assertTrue(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Week week = new Week(1);
        Person person1 = new Person(1, new HashSet<>(), null);
        Person person2 = new Person(2, new HashSet<>(), null);
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person1.getId());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person2.getId());
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenPersonWasAlreadyInThatDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>(), null);
        Person person2 = new Person(1, new HashSet<>(), null);
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person1.getId());
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person2.getId());
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldBeAbleToGetDaysByPerson() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>(), null);
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person1.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);

        List<DayShiftPeopleTriple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("people")
                .extracting("id")
                .containsExactlyInAnyOrder(1);
    }

    @Test
    void shouldBeAbleToGetTwoPersonsInTheSameDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>(), null);
        Person person2 = new Person(2, new HashSet<>(), null);
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person1.getId());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person2.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        List<DayShiftPeopleTriple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("people")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldBeAbleToGetTwoPersonsInDifferentDay() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>(), null);
        Person person2 = new Person(2, new HashSet<>(), null);
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person1.getId());
        week.addPersonIntoDay(TUE.getFuncDay(), MORNING.getShift(), person2.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);

        List<DayShiftPeopleTriple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("people")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldBeAbleToGetTwoPersonsGivenExceedTheCapacity() {
        Week week = new Week(2);
        Person person1 = new Person(1, new HashSet<>(), null);
        Person person2 = new Person(2, new HashSet<>(), null);
        Person person3 = new Person(3, new HashSet<>(), null);
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person1.getId());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person2.getId());
        week.addPersonIntoDay(MON.getFuncDay(), MORNING.getShift(), person3.getId());

        Set<Person> persons = new HashSet<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        List<DayShiftPeopleTriple> scheduled = week.getScheduleWithPersons(persons);
        assertThat(scheduled)
                .flatExtracting("people")
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);
    }
}
