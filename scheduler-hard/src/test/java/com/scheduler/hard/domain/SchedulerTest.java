package com.scheduler.hard.domain;

import com.scheduler.hard.domain.time.DayTuple;
import com.scheduler.hard.domain.time.Days;
import com.scheduler.hard.domain.time.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.time.Days.FRI;
import static com.scheduler.hard.domain.time.Days.MON;
import static com.scheduler.hard.domain.time.Days.SAT;
import static com.scheduler.hard.domain.time.Days.SUN;
import static com.scheduler.hard.domain.time.Days.THU;
import static com.scheduler.hard.domain.time.Days.TUE;
import static com.scheduler.hard.domain.time.Days.WED;
import static org.assertj.core.api.Assertions.assertThat;

class SchedulerTest {

    private Scheduler scheduler;
    private static final EnumSet<Days> ALL_EXCEPT_SUN = EnumSet.of(MON, TUE, WED, THU, FRI, SAT);
    private static final EnumSet<Days> ALL_EXCEPT_MON = EnumSet.of(SUN, TUE, WED, THU, FRI, SAT);

    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
    }

    @Test
    void shouldBeAbleToSchedule() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, ALL_EXCEPT_SUN));
        Week week = scheduler.schedule(persons, new Week(1));
        List<DayTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleGivenThereIsTwoPersonsForTheSameDay() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, ALL_EXCEPT_SUN));
        persons.add(new Person(2, ALL_EXCEPT_SUN));
        Week week = scheduler.schedule(persons, new Week(2));
        List<DayTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(2)
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleGivenThereIsTwoPersonsForDifferentDays() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, ALL_EXCEPT_SUN));
        persons.add(new Person(2, ALL_EXCEPT_MON));
        Week week = scheduler.schedule(persons, new Week(2));
        List<DayTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(MON))
                .flatExtracting("persons")
                .hasSize(1)
                .extracting("id")
                .containsOnly(2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.getDays().equals(SUN) && !tuple.getDays().equals(MON))
                .flatExtracting("persons")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleOnePersonGivenCapacityExceeds() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, ALL_EXCEPT_SUN));
        persons.add(new Person(2, ALL_EXCEPT_SUN));
        Week week = scheduler.schedule(persons, new Week(1));
        List<DayTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToSchedule2PersonsGivenCapacityExceedsInTwoDays() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, ALL_EXCEPT_SUN));
        persons.add(new Person(2, ALL_EXCEPT_MON));
        persons.add(new Person(3, ALL_EXCEPT_SUN));
        Week week = scheduler.schedule(persons, new Week(1));
        List<DayTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(SUN))
                .flatExtracting("persons")
                .hasSize(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.getDays().equals(MON))
                .flatExtracting("persons")
                .hasSize(1)
                .extracting("id")
                .containsOnly(2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.getDays().equals(SUN) && !tuple.getDays().equals(MON))
                .flatExtracting("persons")
                .hasSize(0);
    }
}
