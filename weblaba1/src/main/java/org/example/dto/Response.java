package org.example.dto;

import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    private boolean success;
    private HitResult currentResult;    // Текущий результат
    private List<HitResult> history;    // История результатов
}
