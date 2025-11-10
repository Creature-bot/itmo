package org.example.service;

import java.math.BigDecimal;

public class InputValidator {

    public static ValidationResult validate(int x, BigDecimal y, float r) {
        int[] allowedX = {-5, -4, -3, -2, -1, 0, 1, 2, 3};
        boolean validX = false;
        for (int allowed : allowedX) {
            if (x == allowed) {
                validX = true;
                break;
            }
        }
        if (!validX) {
            return new ValidationResult(false, "X должен быть одним из: -5, -4, -3, -2, -1, 0, 1, 2, 3");
        }

        double yValue = y.doubleValue();
        if (yValue < -3 || yValue > 5) {
            return new ValidationResult(false, "Y должен быть в диапазоне от -3 до 5");
        }

        float[] allowedR = {1, 1.5f, 2, 2.5f, 3};
        boolean validR = false;
        for (float allowed : allowedR) {
            if (Math.abs(r - allowed) < 1e-6) {
                validR = true;
                break;
            }
        }
        if (!validR) {
            return new ValidationResult(false, "R должен быть одним из: 1, 1.5, 2, 2.5, 3");
        }

        return new ValidationResult(true, "Валидация успешна!");
    }

    public static class ValidationResult {
        private final boolean valid;
        private final String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
    }
}