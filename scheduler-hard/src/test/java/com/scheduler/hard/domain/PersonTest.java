package com.scheduler.hard.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.scheduler.hard.domain.Days.MON;
import static com.scheduler.hard.domain.Days.TUE;

class PersonTest {

    @Test
    void shouldBeAbleToCheckIfDayIsInExclusionList() {
        Set<Days> exclusionList = new HashSet<>();
        exclusionList.add(MON);
        Person person = new Person(1, exclusionList, new HashSet<>());
        boolean dayInExclusionList = person.isDayInExclusionList(MON);
        Assertions.assertTrue(dayInExclusionList);
    }

    @Test
    void shouldBeAbleToCheckIfDayIsNotInExclusionList() {
        Set<Days> exclusionList = new HashSet<>();
        exclusionList.add(MON);
        Person person = new Person(1, exclusionList, new HashSet<>());
        boolean dayInExclusionList = person.isDayInExclusionList(TUE);
        Assertions.assertFalse(dayInExclusionList);
    }
}
