package org.example.service;

import org.example.dto.HitResult;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class CheckService {
    public HitResult checkHit(int x, BigDecimal y, float r) {
        long startTime = System.nanoTime();

        BigDecimal yRounded = y.setScale(5, RoundingMode.HALF_UP);

        boolean result = calculate(x, yRounded, r);

        long executionTime = (System.nanoTime() - startTime) / 1000;

        return HitResult.builder()
                .x(x)
                .y(yRounded)
                .r(r)
                .hit(result)
                .timestamp(java.time.LocalDateTime.now())
                .executionTime(executionTime)
                .build();
    }

    private static boolean calculate(int x, BigDecimal y, float r) {
        double yValue = y.doubleValue();

        // 1-я четверть
        if (x >= 0 && yValue >= 0) {
            return (x * x + yValue * yValue) <= r * r;
        }

        // 2-я четверть
        if (x <= 0 && yValue >= 0) {
            return x >= -r && yValue <= r;
        }

        // 3-я четверть
        if (x <= 0 && yValue <= 0) {
            return yValue >= -x - r/2;
        }

        // 4-я четверть
        return false;
    }
}