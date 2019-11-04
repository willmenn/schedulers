package com.scheduler.hard.domain.time;

import com.scheduler.hard.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.scheduler.hard.domain.time.Days.FRI;
import static com.scheduler.hard.domain.time.Days.MON;
import static com.scheduler.hard.domain.time.Days.SAT;
import static com.scheduler.hard.domain.time.Days.SUN;
import static com.scheduler.hard.domain.time.Days.THU;
import static com.scheduler.hard.domain.time.Days.TUE;
import static com.scheduler.hard.domain.time.Days.WED;
import static java.util.stream.Collectors.toList;

public class Week {
    private final List<Day> days;

    public Week(int size) {
        this.days = buildEmptyDays(size);
    }

    private List<Day> buildEmptyDays(int size) {
        List<Day> days = new ArrayList<>();
        days.add(new Day(SUN, size));
        days.add(new Day(MON, size));
        days.add(new Day(TUE, size));
        days.add(new Day(WED, size));
        days.add(new Day(THU, size));
        days.add(new Day(FRI, size));
        days.add(new Day(SAT, size));
        return days;
    }

    public boolean addPersonIntoDay(Days day, Person person) {
        return this.days
                .stream()
                .filter(d -> d.getDay().equals(day))
                .findFirst()
                .map(d -> d.addUniquePerson(person.getId()))
                .orElse(false);
    }

    public boolean isPersonScheduledForDay(Days day, Person person) {
        return this.days
                .stream()
                .filter(d -> d.getDay().equals(day))
                .findFirst()
                .map(d -> d.isPersonAtSchedule(person.getId()))
                .orElse(false);
    }

    public List<DayTuple> getScheduleWithPersons(Set<Person> persons) {
        return this.days
                .stream()
                .map(d -> new DayTuple(d.getDay(), d.getPersonsScheduled(persons)))
                .collect(toList());
    }
}
