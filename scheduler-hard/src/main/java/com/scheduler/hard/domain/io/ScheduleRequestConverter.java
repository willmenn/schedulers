package com.scheduler.hard.domain.io;

import com.scheduler.hard.domain.Day;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import com.scheduler.hard.domain.PlaceDayShiftTuple;
import com.scheduler.hard.domain.Week;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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

     Set<Person> toPeople(@RequestBody ScheduleRequest request) {
        return request.getPeople()
                .stream()
                .map(person ->
                        new Person(person.getId(),
                                createDayShiftExclusion(person.getDayShiftExclusionOrDefault()),
                                new HashSet<>(person.getPlaceExclusionOrDefault()))
                ).collect(Collectors.toSet());
    }

    private Set<PlaceDayShiftTuple> createDayShiftExclusion(List<ScheduleRequest.ShiftExclusion> shiftExclusion) {
        Map<Week.Days, Map<Day.Shifts, List<ScheduleRequest.ShiftExclusion>>> dayShiftGrouped = shiftExclusion
                .stream()
                .collect(groupingBy(ScheduleRequest.ShiftExclusion::getDay, groupingBy(ScheduleRequest.ShiftExclusion::getShift)));

        return dayShiftGrouped.entrySet().stream()
                .map(entry -> new PlaceDayShiftTuple(entry.getKey(),
                        checkIfShiftIsPresent(entry.getValue(), Day.Shifts.MORNING),
                        checkIfShiftIsPresent(entry.getValue(), Day.Shifts.AFTERNOON),
                        checkIfShiftIsPresent(entry.getValue(), Day.Shifts.NIGHT)))
                .collect(Collectors.toSet());
    }

    private Day.Shifts checkIfShiftIsPresent(Map<Day.Shifts, List<ScheduleRequest.ShiftExclusion>> entry, Day.Shifts shift) {
        return entry.containsKey(shift) ? shift : null;
    }

}
