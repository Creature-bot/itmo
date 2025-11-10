package org.example.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitResult {
    private int x;
    private BigDecimal y;
    private float r;
    private boolean hit;                // Результат проверки
    private LocalDateTime timestamp;    // Время проверки
    private long executionTime;         // Время работы
}