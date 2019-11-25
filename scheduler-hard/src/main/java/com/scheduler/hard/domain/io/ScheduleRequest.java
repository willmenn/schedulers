package com.scheduler.hard.domain.io;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scheduler.hard.domain.Day;
import com.scheduler.hard.domain.Week;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ScheduleRequest {
    @NotNull
    @Size(min = 1)
    private Set<PersonRequest> people;
    @Min(1)
    private int size;
    @NotNull
    @Size(min = 1)
    @Valid
    private List<Integer> placeNames;

    public ScheduleRequest() {
    }

    public Set<PersonRequest> getPeople() {
        return people;
    }

    public int getSize() {
        return size;
    }

    public List<Integer> getPlaceNames() {
        return placeNames;
    }


    static class PersonRequest {
        @NotNull
        private Integer id;
        @Valid
        private List<ShiftExclusion> dayShiftExclusion;
        private List<Integer> placeExclusion;

        public PersonRequest() {
        }

        public Integer getId() {
            return id;
        }

        public List<ShiftExclusion> getDayShiftExclusion() {
            return dayShiftExclusion;
        }

        public List<Integer> getPlaceExclusion() {
            return placeExclusion;
        }

        @JsonIgnore
        List<ShiftExclusion> getDayShiftExclusionOrDefault() {
            return Objects.requireNonNullElseGet(this.dayShiftExclusion, ArrayList::new);
        }

        @JsonIgnore
        List<Integer> getPlaceExclusionOrDefault() {
            return Objects.requireNonNullElseGet(this.placeExclusion, ArrayList::new);
        }
    }

    static class ShiftExclusion {
        @NotNull
        private Week.Days day;
        @NotNull
        private Day.Shifts shift;

        public ShiftExclusion() {
        }

        public Week.Days getDay() {
            return day;
        }

        public Day.Shifts getShift() {
            return shift;
        }
    }
}
