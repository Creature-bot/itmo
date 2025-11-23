package se.ifmo.lab2WEB.service;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.ZERO;

public class HitDetector {

    private static final MathContext MC = new MathContext(100);

    public boolean identifyHit(BigDecimal x, BigDecimal y, BigDecimal R) {

        if (x.compareTo(ZERO) >= 0 && y.compareTo(ZERO) >= 0) {
            return x.compareTo(R) <= 0 && y.compareTo(R) <= 0;
        }

        else if (x.compareTo(ZERO) <= 0 && y.compareTo(ZERO) >= 0) {
            boolean x_range = x.compareTo(R.negate()) >= 0;

            boolean y_range = y.compareTo(R) <= 0;

            BigDecimal x_plus_R = x.add(R, MC);
            boolean y_le_x_plus_R = y.compareTo(x_plus_R) <= 0;
            return x_range && y_range && y_le_x_plus_R;
        }

        else if (x.compareTo(ZERO) <= 0 && y.compareTo(ZERO) < 0) {
            BigDecimal sum_sq = x.multiply(x, MC).add(y.multiply(y, MC), MC);
            BigDecimal R_squared = R.multiply(R, MC);
            return sum_sq.compareTo(R_squared) <= 0;
        }

        else {
            return false;
        }
    }
}