package com.scheduler.hard;

import com.scheduler.hard.ScheduleNormalizer.ScheduleAccumulator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class MeanScheduleCalculator {

    public BigDecimal calculateMean(Map<Integer, ScheduleAccumulator> peopleScheduleQuantity, int peopleQtd) {
        int sum = peopleScheduleQuantity.values()
                .stream()
                .mapToInt(ScheduleAccumulator::getSum)
                .sum();

        return BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(peopleQtd), 2, RoundingMode.FLOOR);
    }
}
