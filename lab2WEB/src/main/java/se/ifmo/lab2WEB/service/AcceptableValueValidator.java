package se.ifmo.lab2WEB.service;

import se.ifmo.lab2WEB.dto.HitResultDto;
import se.ifmo.lab2WEB.exception.InvalidDataException;

import java.math.BigDecimal;
import java.util.Arrays;

public class AcceptableValueValidator {
    private final String ERROR_DATA_MESSAGE = "Данные не прошли валидацию: ";

    public void verifyData(HitResultDto hitResultDto) {
        BigDecimal x = hitResultDto.getX();
        BigDecimal y = hitResultDto.getY();
        BigDecimal r = hitResultDto.getR();


        verifyX(x);
        verifyR(r);
        verifyY(y);
    }

    private void verifyY(BigDecimal y) {
        BigDecimal LOWER_BOUND = new BigDecimal("-5");
        BigDecimal UPPER_BOUND = new BigDecimal("5");

        if (!(y.compareTo(LOWER_BOUND) >= 0 && y.compareTo(UPPER_BOUND) <= 0)) {
            throwError("y не является числом от -5 до 5");
        }
    }

    private void verifyX(BigDecimal x) {
        BigDecimal LOWER_BOUND = new BigDecimal("-4");
        BigDecimal UPPER_BOUND = new BigDecimal("4");

        if (!(x.compareTo(LOWER_BOUND) >= 0 && x.compareTo(UPPER_BOUND) <= 0)) {
            throwError("x не является числом от -4 до 4");
        }
    }

    private void verifyR(BigDecimal r) {
        BigDecimal[] allowedRValues = {
                new BigDecimal("1"),
                new BigDecimal("2"),
                new BigDecimal("3"),
                new BigDecimal("4"),
                new BigDecimal("5")
        };
        boolean isAllowed = Arrays.stream(allowedRValues)
                .anyMatch(val -> val.compareTo(r) == 0);
        if (!isAllowed) {
            throwError("r не является числом из предложенной выборки");
        }
    }

    private void throwError(String errorMessage) {
        throw new InvalidDataException("%s %s".formatted(ERROR_DATA_MESSAGE, errorMessage));
    }
}
