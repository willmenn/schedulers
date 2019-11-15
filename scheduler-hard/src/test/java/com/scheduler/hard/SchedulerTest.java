package com.scheduler.hard;

import com.scheduler.hard.domain.DayShiftTuple;
import com.scheduler.hard.domain.DayPeopleTuple;
import com.scheduler.hard.domain.Days;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.scheduler.hard.domain.Days.FRI;
import static com.scheduler.hard.domain.Days.MON;
import static com.scheduler.hard.domain.Days.SAT;
import static com.scheduler.hard.domain.Days.SUN;
import static com.scheduler.hard.domain.Days.THU;
import static com.scheduler.hard.domain.Days.TUE;
import static com.scheduler.hard.domain.Days.WED;
import static com.scheduler.hard.domain.Shifts.AFTERNOON;
import static com.scheduler.hard.domain.Shifts.MORNING;
import static com.scheduler.hard.domain.Shifts.NIGHT;
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
        persons.add(new Person(1, allExceptSun()));
        Week week = scheduler.schedule(persons, new Week(1));
        List<DayPeopleTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleGivenThereIsTwoPersonsForTheSameDay() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSun()));
        persons.add(new Person(2, allExceptSun()));
        Week week = scheduler.schedule(persons, new Week(2));
        List<DayPeopleTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(2)
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleGivenThereIsTwoPersonsForDifferentDays() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSun()));
        persons.add(new Person(2, allExceptMon()));
        Week week = scheduler.schedule(persons, new Week(2));
        List<DayPeopleTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(MON, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayPeopleTuple(SUN, new HashSet<>()))
                        && !tuple.equals(new DayPeopleTuple(MON, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleOnePersonGivenCapacityExceeds() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSun()));
        persons.add(new Person(2, allExceptSun()));
        Week week = scheduler.schedule(persons, new Week(1));
        List<DayPeopleTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToSchedule2PersonsGivenCapacityExceedsInTwoDays() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSun()));
        persons.add(new Person(2, allExceptMon()));
        persons.add(new Person(3, allExceptSun()));
        Week week = scheduler.schedule(persons, new Week(1));
        List<DayPeopleTuple> scheduleWithPersons = week.getScheduleWithPersons(persons);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(SUN, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayPeopleTuple(MON, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayPeopleTuple(SUN, new HashSet<>()))
                        && !tuple.equals(new DayPeopleTuple(MON, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    private Set<DayShiftTuple> allExceptSun() {
        return ALL_EXCEPT_SUN
                .stream()
                .map(buildAllDayExclusion())
                .collect(Collectors.toSet());
    }

    private Set<DayShiftTuple> allExceptMon() {
        return ALL_EXCEPT_MON
                .stream()
                .map(buildAllDayExclusion())
                .collect(Collectors.toSet());
    }

    private Function<Days, DayShiftTuple> buildAllDayExclusion() {
        return day -> new DayShiftTuple(day, MORNING, AFTERNOON, NIGHT);
    }
}
