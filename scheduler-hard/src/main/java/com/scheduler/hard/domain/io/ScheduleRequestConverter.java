package com.scheduler.hard.domain.io;

import com.scheduler.hard.domain.Day.Shifts;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import com.scheduler.hard.domain.PlaceDayShiftTuple;
import com.scheduler.hard.domain.Week;
import com.scheduler.hard.domain.io.ScheduleRequest.ShiftExclusion;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class ScheduleRequestConverter {

    Set<Place> toPlaces(List<Integer> request, int size) {
        return request
                .stream()
                .map(id -> new Place(id, size))
                .collect(Collectors.toSet());
    }

    Set<Person> toPeople(ScheduleRequest request) {
        return request.getPeople()
                .stream()
                .map(person ->
                        new Person(person.getId(),
                                createDayShiftExclusion(person.getDayShiftExclusionOrDefault()),
                                new HashSet<>(person.getPlaceExclusionOrDefault()))
                ).collect(Collectors.toSet());
    }

    private Set<PlaceDayShiftTuple> createDayShiftExclusion(List<ShiftExclusion> shiftExclusion) {
        Map<Week.Days, Map<Shifts, List<ShiftExclusion>>> dayShiftGrouped = shiftExclusion
                .stream()
                .collect(groupingBy(ShiftExclusion::getDay,
                        groupingBy(ShiftExclusion::getShift)));

        return dayShiftGrouped.entrySet().stream()
                .map(entry -> new PlaceDayShiftTuple(entry.getKey(),
                        checkIfShiftIsPresent(entry.getValue(), Shifts.MORNING),
                        checkIfShiftIsPresent(entry.getValue(), Shifts.AFTERNOON),
                        checkIfShiftIsPresent(entry.getValue(), Shifts.NIGHT)))
                .collect(Collectors.toSet());
    }

    private Shifts checkIfShiftIsPresent(Map<Shifts, List<ShiftExclusion>> entry,
                                         Shifts shift) {
        return entry.containsKey(shift) ? shift : null;
    }
}
