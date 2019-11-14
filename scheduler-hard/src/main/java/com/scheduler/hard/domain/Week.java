package com.scheduler.hard.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.scheduler.hard.domain.Days.FRI;
import static com.scheduler.hard.domain.Days.MON;
import static com.scheduler.hard.domain.Days.SAT;
import static com.scheduler.hard.domain.Days.SUN;
import static com.scheduler.hard.domain.Days.THU;
import static com.scheduler.hard.domain.Days.TUE;
import static com.scheduler.hard.domain.Days.WED;
import static java.util.stream.Collectors.toList;

public class Week {
    private final Day sun;
    private final Day mon;
    private final Day tue;
    private final Day wed;
    private final Day thu;
    private final Day fri;
    private final Day sat;

    public Week(int size) {
        this.sun = new Day(SUN, size);
        this.mon = new Day(MON, size);
        this.tue = new Day(TUE, size);
        this.wed = new Day(WED, size);
        this.thu = new Day(THU, size);
        this.fri = new Day(FRI, size);
        this.sat = new Day(SAT, size);
    }

    public boolean addPersonIntoDay(Days day, Shifts shift, Integer id) {
        return day.getDay().apply(this).addUniquePerson(id, shift.getShift());
    }

    public List<DayTuple> getScheduleWithPersons(Set<Person> persons) {
        return StreamAllDays()
                .map(d -> new DayTuple(d.getDay(), d.getPersonsScheduled(persons)))
                .collect(toList());
    }

    private Stream<Day> StreamAllDays() {
        return Stream.of(this.sun, this.mon, this.tue, this.wed, this.thu, this.fri, this.sat);
    }

    Day getSun() {
        return sun;
    }

    Day getMon() {
        return mon;
    }

    Day getTue() {
        return tue;
    }

    Day getWed() {
        return wed;
    }

    Day getThu() {
        return thu;
    }

    Day getFri() {
        return fri;
    }

    Day getSat() {
        return sat;
    }
}
