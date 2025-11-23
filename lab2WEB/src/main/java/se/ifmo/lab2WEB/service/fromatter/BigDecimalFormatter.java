package se.ifmo.lab2WEB.service.fromatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BigDecimalFormatter {

    private static final DecimalFormatSymbols US_SYMBOLS = new DecimalFormatSymbols(Locale.US);
    private static final DecimalFormat EXPONENTIAL_FORMAT = new DecimalFormat("0.########E0", US_SYMBOLS);

    public static String formatBigDecimal(BigDecimal value) {
        if (value == null) {
            return null;
        }
        BigDecimal abs = value.abs();
        String plainString = value.toPlainString();
        int decimalPlaces = 0;
        int dotIndex = plainString.indexOf('.');
        if (dotIndex != -1) {
            decimalPlaces = plainString.length() - dotIndex - 1;
        }

        if (abs.compareTo(new BigDecimal("1000000")) >= 0 ||
                (abs.compareTo(BigDecimal.ZERO) > 0 && abs.compareTo(new BigDecimal("0.001")) < 0) ||
                decimalPlaces > 5) {

            return EXPONENTIAL_FORMAT.format(value);
        } else {
            return value.toPlainString();
        }
    }
}