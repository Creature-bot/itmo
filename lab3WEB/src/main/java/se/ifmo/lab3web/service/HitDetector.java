package se.ifmo.lab3web.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.ZERO;

@Named
@ApplicationScoped
public class HitDetector {

    private static final MathContext MC = new MathContext(100);
    private static final BigDecimal TWO = new BigDecimal(2);

    public boolean identifyHit(BigDecimal x, BigDecimal y, BigDecimal R) {
        if (R.compareTo(ZERO) <= 0) return false;

        final BigDecimal R_HALF = R.divide(TWO, MC);
        final BigDecimal R_HALF_NEGATE = R_HALF.negate();
        final BigDecimal R_NEGATE = R.negate();

        if (x.compareTo(ZERO) >= 0 && y.compareTo(ZERO) >= 0) {
            return false;
        } else if (x.compareTo(ZERO) <= 0 && y.compareTo(ZERO) >= 0) {

            boolean isInCircle = x.multiply(x, MC)
                    .add(y.multiply(y, MC), MC)
                    .compareTo(R_HALF.multiply(R_HALF, MC)) <= 0;

            boolean x_range = x.compareTo(R_HALF_NEGATE) >= 0;
            boolean y_range = y.compareTo(R_HALF) <= 0;

            return isInCircle && x_range && y_range;
        } else if (x.compareTo(ZERO) <= 0 && y.compareTo(ZERO) <= 0) {

            boolean x_range = x.compareTo(R_NEGATE) >= 0;
            boolean y_range = y.compareTo(R_HALF_NEGATE) >= 0;

            return x_range && y_range;
        } else if (x.compareTo(ZERO) >= 0 && y.compareTo(ZERO) <= 0) {

            boolean x_range = x.compareTo(R_HALF) <= 0;
            boolean y_range = y.compareTo(R_HALF_NEGATE) >= 0;

            BigDecimal x_minus_y = x.subtract(y, MC);
            boolean isAboveLine = x_minus_y.compareTo(R_HALF) <= 0;

            return x_range && y_range && isAboveLine;
        } else {
            return false;
        }
    }
}