package com.scheduler.hard.domain.io;

import com.scheduler.hard.Scheduler;
import com.scheduler.hard.domain.Person;
import com.scheduler.hard.domain.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final Scheduler scheduler;
    private final ScheduleRequestConverter scheduleRequestConverter;
    private final ScheduleResponseConverter scheduleResponseConverter;

    @Autowired
    public ScheduleController(Scheduler scheduler, ScheduleRequestConverter scheduleRequestConverter,
                              ScheduleResponseConverter scheduleResponseConverter) {
        this.scheduler = scheduler;
        this.scheduleRequestConverter = scheduleRequestConverter;
        this.scheduleResponseConverter = scheduleResponseConverter;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Set<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        Set<Person> people = scheduleRequestConverter.toPeople(request);

        Set<Place> schedule = scheduler.schedule(people,
                scheduleRequestConverter.toPlaces(request.getPlaceNames(), request.getSize()));

        return scheduleResponseConverter.fromPlaces(schedule, people);
    }
}
