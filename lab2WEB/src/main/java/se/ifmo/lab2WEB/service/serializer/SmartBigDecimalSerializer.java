package se.ifmo.lab2WEB.service.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SmartBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    private static final DecimalFormatSymbols US_SYMBOLS = new DecimalFormatSymbols(Locale.US);

    private static final DecimalFormat EXPONENTIAL_FORMAT = new DecimalFormat("0.########E0", US_SYMBOLS);

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
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

            gen.writeString(EXPONENTIAL_FORMAT.format(value));
        } else {
            gen.writeString(value.toPlainString());
        }
    }
}
