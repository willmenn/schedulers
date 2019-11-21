package com.scheduler.hard;

import com.scheduler.hard.domain.Day;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import com.scheduler.hard.domain.Shift;
import com.scheduler.hard.domain.Shifts;
import com.scheduler.hard.domain.Week;
import com.scheduler.hard.domain.Week.Days;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Scheduler {

    // Normalizar por um parametro.
    // As Pessoas tem que estar mais ou menos com a mesma distribuicao entre os dias.

    public Set<Place> schedule(Set<Person> persons, Set<Place> places) {
        persons.stream()
                .map(person -> createTuplePersonByDayShift(person, places))
                .flatMap(Collection::stream)
                .forEach(tuple -> addIntoPlaceIfPresent(places, tuple));

        return places;
    }

    private void addIntoPlaceIfPresent(Set<Place> places, PersonTuple tuple) {
        places
                .stream()
                .filter(place -> tuple.isPlaceAllowedToBeScheduled(place.getId()))
                .filter(place -> !place.isPersonScheduled(tuple.getDayFunc(), tuple.getShift(), tuple.getId()))
                .findFirst()
                .ifPresent(place -> place.addUniquePerson(tuple.getDayFunc(), tuple.getShift(), tuple.getId()));
    }

    private Set<PersonTuple> createTuplePersonByDayShift(Person person, Set<Place> places) {
        return Days.all
                .stream()
                .map(d -> addShiftsOnlyIfItsNotInExclusionList(person, d, places))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<PersonTuple> addShiftsOnlyIfItsNotInExclusionList(Person person, Days d, Set<Place> places) {
        return Shifts.all.stream()
                .filter(shift -> !person.isDayAndShiftInExclusionList(d, shift))
                .map(shift ->
                        new PersonTuple(d.getFuncDay(), person.getId(), shift.getShift(),
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

    private class PersonTuple {
        private final Function<Week, Day> day;
        private final Integer id;
        private final Function<Day, Shift> shift;
        private final Set<Integer> placeIds;

        PersonTuple(Function<Week, Day> day, Integer id, Function<Day, Shift> shift, Set<Integer> placeIds) {
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
            PersonTuple that = (PersonTuple) o;
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
