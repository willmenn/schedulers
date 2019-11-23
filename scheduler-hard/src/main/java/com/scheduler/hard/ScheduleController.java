package com.scheduler.hard;

import com.scheduler.hard.domain.Day.Shifts;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import com.scheduler.hard.domain.PlaceDayShiftTuple;
import com.scheduler.hard.domain.Week.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final Scheduler scheduler;

    @Autowired
    public ScheduleController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Set<Place> createSchedule(@RequestBody ScheduleRequest request) {
        return scheduler.schedule(createPerson(request), createPlaces(request.placeNames, request.size));
    }

    private Set<Person> createPerson(@RequestBody ScheduleRequest request) {
        return request.people
                .stream()
                .map(person ->
                        new Person(person.id,
                                createDayShiftExclusion(person.dayShiftExclusion),
                                new HashSet<>(person.placeExclusion))
                ).collect(Collectors.toSet());
    }

    private Set<PlaceDayShiftTuple> createDayShiftExclusion(List<ShiftExclusion> shiftExclusion) {
        Map<Days, Map<Shifts, List<ShiftExclusion>>> dayShiftGrouped = shiftExclusion
                .stream()
                .collect(groupingBy(ShiftExclusion::getDay, groupingBy(ShiftExclusion::getShift)));

        return dayShiftGrouped.entrySet().stream()
                .map(entry -> new PlaceDayShiftTuple(entry.getKey(),
                        checkIfShiftIsPresent(entry.getValue(), Shifts.MORNING),
                        checkIfShiftIsPresent(entry.getValue(), Shifts.AFTERNOON),
                        checkIfShiftIsPresent(entry.getValue(), Shifts.NIGHT)))
                .collect(Collectors.toSet());
    }

    private Shifts checkIfShiftIsPresent(Map<Shifts, List<ShiftExclusion>> entry, Shifts shift) {
        return entry.containsKey(shift) ? shift : null;
    }

    private Set<Place> createPlaces(List<Integer> request, int size) {
        return request
                .stream()
                .map(id -> new Place(id, size))
                .collect(Collectors.toSet());
    }

    private static class ScheduleRequest {
        @NotNull
        @Size(min = 1)
        private Set<PersonRequest> people;
        @Min(1)
        private int size;
        @NotNull
        @Size(min = 1)
        private List<Integer> placeNames;

        public ScheduleRequest(Set<PersonRequest> people, int size, List<Integer> placeNames) {
            this.people = people;
            this.size = size;
            this.placeNames = placeNames;
        }
    }

    private static class PersonRequest {
        private Integer id;
        private List<ShiftExclusion> dayShiftExclusion;
        private List<Integer> placeExclusion;

        public PersonRequest(Integer id, List<ShiftExclusion> dayShiftExclusion, List<Integer> placeExclusion) {
            this.id = id;
            this.dayShiftExclusion = dayShiftExclusion;
            this.placeExclusion = placeExclusion;
        }
    }

    private static class ShiftExclusion {
        private Days day;
        private Shifts shift;

        private ShiftExclusion(Days day, Shifts shift) {
            this.day = day;
            this.shift = shift;
        }

        public Days getDay() {
            return day;
        }

        public Shifts getShift() {
            return shift;
        }
    }
}
