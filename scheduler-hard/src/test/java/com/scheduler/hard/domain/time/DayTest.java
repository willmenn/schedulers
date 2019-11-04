package com.scheduler.hard.domain.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.scheduler.hard.domain.time.Days.MON;
import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    @Test
    void shouldBeAbleToCheckIfDayIsEmpty() {
        Day day = new Day(MON, 10);
        assertTrue(day.isDayEmpty());
    }

    @Test
    void shouldBeAbleToCheckIfDayIsNotEmpty() {
        Day day = new Day(MON, 10);
        day.addUniquePerson(1);
        assertFalse(day.isDayEmpty());
    }

    @Test
    void shouldBeAbleToCheckIfDayIsFull() {
        Day day = new Day(MON, 1);
        day.addUniquePerson(1);
        assertTrue(day.isDayFull());
    }

    @Test
    void shouldBeAbleToCheckIfDayIsNotFull() {
        Day day = new Day(MON, 10);
        assertFalse(day.isDayFull());
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsAtTheDay() {
        Day day = new Day(MON, 10);
        day.addUniquePerson(1);
        assertTrue(day.isPersonAtSchedule(1).isPresent());
    }

    @Test
    void shouldBeAbleToCheckIfPersonIsNotAtTheDay() {
        Day day = new Day(MON, 10);
        day.addUniquePerson(1);
        assertFalse(day.isPersonAtSchedule(2).isPresent());
    }

    @Test
    void shouldBeAbleToAddAUniquePersonToADay() {
        Day day = new Day(MON, 10);
        assertTrue(day.addUniquePerson(1));
    }

    @Test
    void shouldNotBeAbleToAddPersonGivenDayIsFull() {
        Day day = new Day(MON, 1);
        Assertions.assertTrue(day.addUniquePerson(1));
        assertFalse(day.addUniquePerson(2));
    }

    @Test
    void shouldNotBeAbleToAddAPersonGivenSheIsNotUnique() {
        Day day = new Day(MON, 1);
        Assertions.assertTrue(day.addUniquePerson(1));
        assertFalse(day.addUniquePerson(1));
    }
}
