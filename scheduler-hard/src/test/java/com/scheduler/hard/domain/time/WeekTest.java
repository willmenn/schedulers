package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.scheduler.hard.domain.time.Days.MON;
import static com.scheduler.hard.domain.time.Days.TUE;
import static org.junit.jupiter.api.Assertions.*;

class WeekTest {

    @Test
    void shouldBeAbleToAddPersonIntoDayGivenDayIsNotFull() {
        Week week = new Week(2);
        Person person = new Person(1);
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON, person);
        Assertions.assertTrue(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Week week = new Week(1);
        Person person1 = new Person(1);
        Person person2 = new Person(2);
        week.addPersonIntoDay(MON, person1);
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON, person2);
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenPersonWasAlreadyInThatDay() {
        Week week = new Week(2);
        Person person1 = new Person(1);
        Person person2 = new Person(1);
        week.addPersonIntoDay(MON, person1);
        boolean isAbleToAddPerson = week.addPersonIntoDay(MON, person2);
        Assertions.assertFalse(isAbleToAddPerson);
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsAtACertainDay() {
        Week week = new Week(2);
        Person person1 = new Person(1);
        week.addPersonIntoDay(MON, person1);

        boolean isPersonScheduled = week.isPersonScheduledForDay(MON, person1);
        Assertions.assertTrue(isPersonScheduled);
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsNotAtACertainDayGivenDayIsEmpty() {
        Week week = new Week(2);
        Person person1 = new Person(1);
        week.addPersonIntoDay(MON, person1);

        boolean isPersonScheduled = week.isPersonScheduledForDay(TUE, person1);
        Assertions.assertFalse(isPersonScheduled);
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsNotAtACertainDay() {
        Week week = new Week(2);
        Person person1 = new Person(1);
        Person person2 = new Person(2);
        week.addPersonIntoDay(MON, person1);

        boolean isPersonScheduled = week.isPersonScheduledForDay(MON, person2);
        Assertions.assertFalse(isPersonScheduled);
    }
}
