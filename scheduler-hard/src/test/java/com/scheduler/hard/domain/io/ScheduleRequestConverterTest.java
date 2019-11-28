package com.scheduler.hard.domain.io;

import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.scheduler.hard.domain.Day.Shifts.AFTERNOON;
import static com.scheduler.hard.domain.Day.Shifts.MORNING;
import static com.scheduler.hard.domain.Day.Shifts.NIGHT;
import static com.scheduler.hard.domain.Week.Days.MON;
import static com.scheduler.hard.domain.io.ScheduleRequest.PersonRequest;
import static com.scheduler.hard.domain.io.ScheduleRequest.ShiftExclusion;
import static org.assertj.core.api.Assertions.assertThat;

class ScheduleRequestConverterTest {

    private ScheduleRequestConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ScheduleRequestConverter();
    }

    @Test
    void shouldBeAbleFromASchedulerRequestToCreatePeopleGivenNoExclusion() {

        ArrayList<Integer> placeNames = new ArrayList<>();
        int placeId = 1;
        placeNames.add(placeId);
        HashSet<PersonRequest> people = new HashSet<>();
        people.add(new PersonRequest(1, null, null));
        int size = 1;

        ScheduleRequest request = new ScheduleRequest(people, size, placeNames);

        Set<Person> response = converter.toPeople(request);

        assertThat(response)
                .hasSize(1)
                .extracting("id")
                .containsExactlyInAnyOrder(1);

        assertThat(response)
                .flatExtracting("dayShiftExclusionList")
                .hasSize(0)
                .isNotNull();

        assertThat(response)
                .flatExtracting("placeIds")
                .hasSize(0)
                .isNotNull();
    }

    @Test
    void shouldBeAbleToConvertScheduleRequestIntoPeopleGivenAWholeDayAsExclusion() {
        ArrayList<Integer> placeNames = new ArrayList<>();
        int placeId = 1;
        placeNames.add(placeId);
        HashSet<PersonRequest> people = new HashSet<>();
        List<ShiftExclusion> dayExclusions = new ArrayList<>();
        dayExclusions.add(new ShiftExclusion(MON, MORNING));
        dayExclusions.add(new ShiftExclusion(MON, AFTERNOON));
        dayExclusions.add(new ShiftExclusion(MON, NIGHT));
        people.add(new PersonRequest(1, dayExclusions, null));
        int size = 1;

        ScheduleRequest request = new ScheduleRequest(people, size, placeNames);

        Set<Person> response = converter.toPeople(request);

        assertThat(response)
                .hasSize(1)
                .extracting("id")
                .containsExactlyInAnyOrder(1);

        assertThat(response)
                .flatExtracting("dayShiftExclusionList")
                .hasSize(1)
                .isNotNull()
        .extracting("day")
        .containsExactlyInAnyOrder(MON);

        assertThat(response)
                .flatExtracting("dayShiftExclusionList")
                .flatExtracting("shifts")
                .hasSize(3)
                .containsExactlyInAnyOrder(MORNING,AFTERNOON,NIGHT);

        assertThat(response)
                .flatExtracting("placeIds")
                .hasSize(0)
                .isNotNull();
    }

    @Test
    void shouldBeAbleToConvertScheduleRequestToPeopleGivenHasPlaceExclusion() {
        ArrayList<Integer> placeNames = new ArrayList<>();
        int placeId = 1;
        placeNames.add(placeId);
        placeNames.add(2);
        HashSet<PersonRequest> people = new HashSet<>();
        List<Integer> placeExclusion = new ArrayList<>();
        placeExclusion.add(2);
        people.add(new PersonRequest(1, null, placeExclusion));
        int size = 1;

        ScheduleRequest request = new ScheduleRequest(people, size, placeNames);

        Set<Person> response = converter.toPeople(request);

        assertThat(response)
                .hasSize(1)
                .extracting("id")
                .containsExactlyInAnyOrder(1);

        assertThat(response)
                .flatExtracting("dayShiftExclusionList")
                .hasSize(0)
                .isNotNull();

        assertThat(response)
                .flatExtracting("placeIds")
                .hasSize(1)
                .isNotNull()
        .containsExactlyInAnyOrder(2);
    }

    @Test
    void shouldBeAbleToConvertAListOfPlaceAsIntegerToThePlace() {
        List<Integer> places = new ArrayList<>();
        places.add(1);
        Set<Place> response = converter.toPlaces(places, 2);

        assertThat(response)
                .extracting("id")
                .containsExactly(1);

        assertThat(response)
                .extracting("week")
                .extracting("mon")
                .extracting("morning")
                .extracting("capacity")
                .containsExactly(2);
    }
}
