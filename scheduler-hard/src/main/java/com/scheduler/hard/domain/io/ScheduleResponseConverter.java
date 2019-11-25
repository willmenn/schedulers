package com.scheduler.hard.domain.io;

import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScheduleResponseConverter {

    Set<ScheduleResponse> fromPlaces(Set<Place> places, Set<Person> people) {
        return places
                .stream()
                .map(place -> new ScheduleResponse(place.getId(), place.getPersonsScheduled(people)))
                .collect(Collectors.toSet());
    }
}
