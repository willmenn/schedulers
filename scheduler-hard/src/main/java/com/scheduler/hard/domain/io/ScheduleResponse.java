package com.scheduler.hard.domain.io;

import com.scheduler.hard.domain.DayShiftPeopleTriple;

import java.util.List;

public class ScheduleResponse {
    private Integer placeId;
    private List<DayShiftPeopleTriple> schedule;

    public ScheduleResponse() {
    }

    ScheduleResponse(Integer placeId, List<DayShiftPeopleTriple> schedule) {
        this.placeId = placeId;
        this.schedule = schedule;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public List<DayShiftPeopleTriple> getSchedule() {
        return schedule;
    }
}

