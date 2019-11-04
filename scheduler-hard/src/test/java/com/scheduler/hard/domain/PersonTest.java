package com.scheduler.hard.domain;

import com.scheduler.hard.domain.time.Days;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.time.Days.MON;
import static com.scheduler.hard.domain.time.Days.TUE;

class PersonTest {

    @Test
    void shouldBeAbleToCheckIfDayIsInExclusionList() {
        Set<Days> exclusionList = new HashSet<>();
        exclusionList.add(MON);
        Person person = new Person(1, exclusionList);
        boolean dayInExclusionList = person.isDayInExclusionList(MON);
        Assertions.assertTrue(dayInExclusionList);
    }

    @Test
    void shouldBeAbleToCheckIfDayIsNotInExclusionList() {
        Set<Days> exclusionList = new HashSet<>();
        exclusionList.add(MON);
        Person person = new Person(1, exclusionList);
        boolean dayInExclusionList = person.isDayInExclusionList(TUE);
        Assertions.assertFalse(dayInExclusionList);
    }
}
