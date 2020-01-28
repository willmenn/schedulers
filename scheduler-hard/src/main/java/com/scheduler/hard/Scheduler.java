package com.scheduler.hard;

import com.scheduler.hard.domain.*;
import com.scheduler.hard.domain.Day.Shifts;
import com.scheduler.hard.domain.Week.Days;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Scheduler {

    // Normalizar por um parametro.
    // As Pessoas tem que estar mais ou menos com a mesma distribuicao entre os dias.

    public Set<Place> schedule(Set<Person> persons, Set<Place> places) {
        persons.stream()
                .map(person -> createTriplePersonByDayShift(person, places))
                .flatMap(Collection::stream)
                .forEach(tuple -> addIntoPlaceIfPresent(places, tuple));

        //getPersonScheduleQty(persons, places);

        return places;
    }


    private void addIntoPlaceIfPresent(Set<Place> places, PlacePersonDayShiftTuple tuple) {
        places
                .stream()
                .filter(place -> tuple.isPlaceAllowedToBeScheduled(place.getId()))
                .filter(place -> !place.isPersonScheduled(tuple.getDayFunc(), tuple.getShift(), tuple.getId()))
                .findFirst()
                .ifPresent(place -> place.addUniquePerson(tuple.getDayFunc(), tuple.getShift(), tuple.getId()));
    }

    private Set<PlacePersonDayShiftTuple> createTriplePersonByDayShift(Person person, Set<Place> places) {
        return Days.all
                .stream()
                .map(d -> addShiftsOnlyIfItsNotInExclusionList(person, d, places))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<PlacePersonDayShiftTuple> addShiftsOnlyIfItsNotInExclusionList(Person person, Days d, Set<Place> places) {
        return Shifts.all.stream()
                .filter(shift -> !person.isDayAndShiftInExclusionList(d, shift))
                .map(shift ->
                        new PlacePersonDayShiftTuple(d.getFuncDay(), person.getId(), shift.getShift(),
                                getPlacesThatAreNotInExclusionList(person, places)
                        )
                )
                .collect(Collectors.toSet());
    }

    private Set<Integer> getPlacesThatAreNotInExclusionList(Person person, Set<Place> places) {
        return places.stream()
                .filter(place -> !person.isPlaceInExclusionList(place.getId()))
                .map(Place::getId)
                .collect(Collectors.toSet());
    }

    //This method is to be able to get the Mean
    private void getPersonScheduleQty(Set<Person> persons, Set<Place> places) {
        Map<Integer, Integer> collect = places
                .stream()
                .flatMap(place -> place.getPersonsScheduled(persons).stream())
                .flatMap(triple -> triple.getPeople().stream()
                        .map(person -> new PersonScheduleSum(person.getId()))
                        .collect(Collectors.toList())
                        .stream())
                .collect(Collectors.toMap(PersonScheduleSum::getId, PersonScheduleSum::getOne, Integer::sum));
    }

    private static class PersonScheduleSum {
        private Integer id;
        private Integer one;

        private PersonScheduleSum(Integer id) {
            this.id = id;
            this.one = 1;
        }

        private Integer getId() {
            return this.id;
        }

        public Integer getOne() {
            return this.one;
        }
    }

    private static class PlacePersonDayShiftTuple {
        private final Function<Week, Day> day;
        private final Integer id;
        private final Function<Day, Shift> shift;
        private final Set<Integer> placeIds;

        private PlacePersonDayShiftTuple(Function<Week, Day> day, Integer id, Function<Day, Shift> shift,
                                         Set<Integer> placeIds) {
            this.day = day;
            this.id = id;
            this.shift = shift;
            this.placeIds = placeIds;
        }

        private Function<Week, Day> getDayFunc() {
            return day;
        }

        private Integer getId() {
            return id;
        }

        private Function<Day, Shift> getShift() {
            return shift;
        }

        private boolean isPlaceAllowedToBeScheduled(Integer id) {
            return this.placeIds.contains(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlacePersonDayShiftTuple that = (PlacePersonDayShiftTuple) o;
            return Objects.equals(day, that.day) &&
                    Objects.equals(id, that.id) &&
                    shift == that.shift;
        }

        @Override
        public int hashCode() {
            return Objects.hash(day, id, shift);
        }
    }
}
