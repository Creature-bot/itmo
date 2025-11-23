package se.ifmo.lab2WEB.service;

import jakarta.servlet.http.HttpServletRequest;
import se.ifmo.lab2WEB.dto.HitResultDto;
import se.ifmo.lab2WEB.exception.InvalidDataException;

import java.math.BigDecimal;

public class RequestValidator {
    private final String ERROR_DATA_MESSAGE = "Данные не прошли валидацию:";

    public HitResultDto getHitResult(HttpServletRequest req) {
        try {
            String xValue = req.getParameter("x");
            String yValue = req.getParameter("y");
            String rValue = req.getParameter("r");

            BigDecimal x = new BigDecimal(xValue);
            BigDecimal y = new BigDecimal(yValue);
            BigDecimal r = new BigDecimal(rValue);

            return new HitResultDto(x, y, r);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("%s Проверьте, что x, y и r являются числами.".formatted(ERROR_DATA_MESSAGE));
        } catch (NullPointerException e) {
            throw new InvalidDataException("%s Проверьте, что x, y и r присутствуют в запросе.".formatted(ERROR_DATA_MESSAGE));
        }
    }
}
