package com.scheduler.hard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class MeanScheduleCalculator {

    // To call this method you need to change Scheduler.class to call this internal method: getPersonScheduleQty
    public BigDecimal calculateMean(Map<Integer, Integer> peopleScheduleQuantity, int peopleQtd) {
        int sum = peopleScheduleQuantity.values()
                .stream()
                .mapToInt(sum1 -> sum1)
                .sum();

        return BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(peopleQtd), 2, RoundingMode.FLOOR);
    }
}
