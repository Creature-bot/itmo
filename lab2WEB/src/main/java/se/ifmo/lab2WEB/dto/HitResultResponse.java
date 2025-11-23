package se.ifmo.lab2WEB.dto;

import lombok.*;
import se.ifmo.lab2WEB.entity.HitResult;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HitResultResponse {
    private HitResult hitResult;
}
