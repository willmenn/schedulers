package com.scheduler.hard;

import com.scheduler.hard.domain.DayShiftPeopleTriple;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import com.scheduler.hard.domain.PlaceDayShiftTuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.scheduler.hard.domain.Shifts.AFTERNOON;
import static com.scheduler.hard.domain.Shifts.MORNING;
import static com.scheduler.hard.domain.Shifts.NIGHT;
import static com.scheduler.hard.domain.Week.Days;
import static com.scheduler.hard.domain.Week.Days.FRI;
import static com.scheduler.hard.domain.Week.Days.MON;
import static com.scheduler.hard.domain.Week.Days.SAT;
import static com.scheduler.hard.domain.Week.Days.SUN;
import static com.scheduler.hard.domain.Week.Days.THU;
import static com.scheduler.hard.domain.Week.Days.TUE;
import static com.scheduler.hard.domain.Week.Days.WED;
import static org.assertj.core.api.Assertions.assertThat;

class SchedulerTest {

    private Scheduler scheduler;
    private static final EnumSet<Days> ALL_EXCEPT_SUN = EnumSet.of(MON, TUE, WED, THU, FRI, SAT);
    private static final EnumSet<Days> ALL_EXCEPT_MON = EnumSet.of(SUN, TUE, WED, THU, FRI, SAT);
    private static final EnumSet<Days> ALL_EXCEPT_WEEKEND = EnumSet.of(SAT, SUN);

    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
    }

    @Test
    void shouldBeAbleToSchedule() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSunMorning(), null));
        Set<Place> places = buildPlacesWithOnePlace(1);
        Set<Place> response = scheduler.schedule(persons, places);
        List<DayShiftPeopleTriple> scheduleWithPersons = getDayShiftPeopleTriples(persons, response);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    private Set<Place> buildPlacesWithOnePlace(int size) {
        Place place = new Place(1, size);
        Set<Place> places = new HashSet<>();
        places.add(place);
        return places;
    }

    private List<DayShiftPeopleTriple> getDayShiftPeopleTriples(Set<Person> persons, Set<Place> response) {
        Place placeResponse = response.stream().findFirst().get();
        return placeResponse.getPersonsScheduled(persons);
    }

    @Test
    void shouldBeAbleToScheduleGivenThereIsTwoPersonsForTheSameDay() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSunMorning(), null));
        persons.add(new Person(2, allExceptSunMorning(), null));
        Set<Place> places = buildPlacesWithOnePlace(2);

        Set<Place> response = scheduler.schedule(persons, places);
        List<DayShiftPeopleTriple> scheduleWithPersons = getDayShiftPeopleTriples(persons, response);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(2)
                .extracting("id")
                .containsExactlyInAnyOrder(1, 2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleGivenThereIsTwoPersonsForDifferentDays() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSunMorning(), null));
        persons.add(new Person(2, allExceptMon(), null));
        Set<Place> places = buildPlacesWithOnePlace(2);

        Set<Place> response = scheduler.schedule(persons, places);
        List<DayShiftPeopleTriple> scheduleWithPersons = getDayShiftPeopleTriples(persons, response);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(MON, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>()))
                        && !tuple.equals(new DayShiftPeopleTriple(MON, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleOnePersonGivenCapacityExceeds() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSunMorning(), null));
        persons.add(new Person(2, allExceptSunMorning(), null));
        Set<Place> places = buildPlacesWithOnePlace(1);
        Set<Place> response = scheduler.schedule(persons, places);
        List<DayShiftPeopleTriple> scheduleWithPersons = getDayShiftPeopleTriples(persons, response);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToSchedule2PersonsGivenCapacityExceedsInTwoDays() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptSunMorning(), null));
        persons.add(new Person(2, allExceptMon(), null));
        persons.add(new Person(3, allExceptSunMorning(), null));
        Set<Place> places = buildPlacesWithOnePlace(1);

        Set<Place> response = scheduler.schedule(persons, places);
        List<DayShiftPeopleTriple> scheduleWithPersons = getDayShiftPeopleTriples(persons, response);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> tuple.equals(new DayShiftPeopleTriple(MON, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(1)
                .extracting("id")
                .containsOnly(2);

        assertThat(scheduleWithPersons)
                .filteredOn(tuple -> !tuple.equals(new DayShiftPeopleTriple(SUN, MORNING, new HashSet<>()))
                        && !tuple.equals(new DayShiftPeopleTriple(MON, MORNING, new HashSet<>())))
                .flatExtracting("people")
                .hasSize(0);
    }

    @Test
    void shouldBeAbleToScheduleATeam() {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person(1, allExceptWeekend(), null));
        persons.add(new Person(2, allExceptWeekend(), null));
        persons.add(new Person(3, allExceptWeekend(), null));
        persons.add(new Person(4, allExceptWeekend(), null));
        persons.add(new Person(5, allExceptWeekend(), null));
        Set<Place> places = buildPlacesWithOnePlace(1);
        Set<Place> response = scheduler.schedule(persons, places);
        List<DayShiftPeopleTriple> scheduleWithPersons = getDayShiftPeopleTriples(persons, response);
        Collections.sort(scheduleWithPersons, DayShiftPeopleTriple.comparator());

        System.out.println(scheduleWithPersons.toString());
    }

    private Set<PlaceDayShiftTuple> allExceptSunMorning() {
        Set<PlaceDayShiftTuple> exclusion = ALL_EXCEPT_SUN
                .stream()
                .map(buildAllDayExclusion())
                .collect(Collectors.toSet());

        exclusion.add(new PlaceDayShiftTuple(SUN, null, AFTERNOON, NIGHT));

        return exclusion;
    }

    private Set<PlaceDayShiftTuple> allExceptMon() {
        Set<PlaceDayShiftTuple> exclusion = ALL_EXCEPT_MON
                .stream()
                .map(buildAllDayExclusion())
                .collect(Collectors.toSet());

        exclusion.add(new PlaceDayShiftTuple(MON, null, AFTERNOON, NIGHT));
        return exclusion;
    }

    private Set<PlaceDayShiftTuple> allExceptWeekend() {
        return ALL_EXCEPT_WEEKEND
                .stream()
                .map(buildAllDayExclusion())
                .collect(Collectors.toSet());
    }

    private Function<Days, PlaceDayShiftTuple> buildAllDayExclusion() {
        return day -> new PlaceDayShiftTuple(day, MORNING, AFTERNOON, NIGHT);
    }
}
